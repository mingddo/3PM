<template>
  <div class="map_wrap">
    <div class="map-location">
      <button class="map-location-btn" @click="getLocation">
        <i class="fas fa-map-marker-alt"></i> 현재 위치
      </button>
    </div>
    <div class="map-space">
      <div class="map-about">
        <input class="map-input" type="text" id="keyword" v-model.trim="keyword" @keyup.enter="searchPlaces">
        <div v-if="items.length < 1" class="map-input-guide">
          <li>
            위치를 공유해보세요
          </li>
          <li>
            위치는 검색해서 찾을 수 있습니다
          </li>
          <li>
            현재 위치를 공유하고 싶다면 지도 우측상단 버튼을 클릭하세요
          </li>
        </div>
        <div id="menu_wrap" class="bg_white map-info" v-show="!fold">
          <div class="option">
          </div>
          <ul id="placesList" class="map-info-list">
            <div class="item" v-for="(p, idx) in items" :key="idx">
              <span :class="`markerbg marker_${index+1}`" @click="select(p)">
                <div class="info" @click="select(p)">
                  <h5>
                    {{ p.place_name }}      
                  </h5>
                  <span v-if="p.road_address_name">
                    <span>
                      {{ p.road_address_name }}
                    </span>
                    <span class="jibun gray">
                      {{ p.address_name }}
                    </span>
                  </span>
                  <span v-else>
                    {{ p.address_name }}
                  </span>
                  <span class="tel">
                    {{ p.phone }}
                  </span>
                </div>
              </span>
            </div>
          </ul>
          <div id="pagination"></div>

          <!-- <div v-for="(item, idx) in items" :key="idx"> 
            <div @click="select(item)">{{ item }}</div>
          </div> -->
        </div>
      </div>
      <div id="map" class="map"></div>
    </div>
    <!-- <span id="centerAddr"></span> -->
  </div>
</template>

<script>
import Swal from 'sweetalert2';

export default {
  props: {
    latitude: Number,
    longitude: Number,
  },
  data () {
    return {
      fold: true,
      selected: false,
      markers: [],
      maps: null,
      place: null,
      infowindow: {},
      keyword: '',
      items: [],
      selectLocation: {},
      // fragment: [],
      index: null,
      placePos: {},
      pageNum: null,
      lat: this.latitude,
      lng: this.longitude,
      type: null,
      location: {},
    }
  },
  mounted() {
    window.kakao && window.kakao.maps
      ? this.setMap()
      : this.addKakaoMapScript();
  },
  methods: {
    displayCenterInfo(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        // var infoDiv = document.getElementById('centerAddr');
        for(let i = 0; i < result.length; i++) {
          this.keyword = result[i].address.address_name
          this.location.placeName = null
          this.location.address = result[i].address.address_name
          this.location.city = result[i].address.region_1depth_name
          this.location.lng = this.lng
          this.location.lat = this.lat
          this.$emit('sendLocation', this.location)
          this.searchPlaces();
          
        }
      }    
    },
    panto (y, x) {
      let moveLatLon = new kakao.maps.LatLng(y,x);
      return this.map.panTo(moveLatLon)
    },
    handleGeoSuccess(position) {
      this.location = {}
      this.lat = position.coords.latitude;
      this.lng = position.coords.longitude;
      this.panto (this.lat, this.lng)
      let firstPlacePosition = new kakao.maps.LatLng(this.lat, this.lng)
      this.addMarker(firstPlacePosition, 0)
      let geocoder = new kakao.maps.services.Geocoder();
      // geocoder.coord2RegionCode(this.lng, this.lat, this.displayCenterInfo);
      geocoder.coord2Address(this.lng, this.lat, this.displayCenterInfo);      
    },
    handleGeoError() {
      console.log("Cant Access geo location");
    },
    getLocation() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
        this.handleGeoSuccess, //위치 정보 얻었을떄 실행되는 함수
        this.handleGeoError);  //위치 정보 error시 실행되는 함수
      } else {
        // alert('GPS를 지원하지 않습니다.')
        Swal.fire('GPS를 지원하지 않습니다', '', 'warning');
      }
    },
    select (item) {
      this.location = {}
      this.selected = true;
      this.selectLocation = item
      this.lat = item.y
      this.lng = item.x
      this.fold = true
      this.location.placeName = item.place_name
      this.location.lng = item.x
      this.location.lat = item.y
      this.location.address = item.address_name
      this.location.city = item.address_name.split(' ')[0]
      this.setMap()
      this.$emit('sendLocation', this.location)
      this.setZoomable(false);
    },
    setMap () {
      let mapContainer = document.getElementById('map'), mapOption = {
        center : new kakao.maps.LatLng(this.lat, this.lng),
        level: 5
      };
      this.map = new kakao.maps.Map(mapContainer, mapOption)
      let firstPlacePosition = new kakao.maps.LatLng(this.lat, this.lng)
      this.addMarker(firstPlacePosition, 0)
    },
    addKakaoMapScript() {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.setMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=4eefdb70288a52331d930637bf0a9161&libraries=services,clusterer,drawing";
      document.head.appendChild(script);
    },
    searchPlaces() {
      if (this.keyword != '') {
        this.fold = false;
        this.place = new kakao.maps.services.Places();
        this.infowindow = new kakao.maps.InfoWindow({zIndex:1});
        this.setZoomable(true);
        this.place.keywordSearch(this.keyword, this.placeSearchCB)
      } else {
        // alert('검색어를 입력해주세요')
        Swal.fire('검색어를 입력해주세요', '', 'error');
      }
    },
    placeSearchCB (data, status, pagination) {
      if (status === kakao.maps.services.Status.OK) {
        // console.log('data', data, pagination)
        this.displayPlaces(data)
        this.displayPagination(pagination)
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        // alert('검색결과가 존재하지 않습니다.')
        Swal.fire('검색결과가 존재하지 않습니다', '', 'error');
      } else if (status === kakao.maps.services.Status.ERROR) {
        // alert('검색 결과 중 오류가 발생했습니다.');
        Swal.fire('검색 결과 중 오류가 발생했습니다', '', 'error');
        return;
      }
    },
    displayInfowindow(marker, title) {
      let content = '<div style="padding:5px;z-index:1;">' + title + '</div>';
      this.infowindow.setContent(content);
      this.infowindow.open(this.map, marker);
    },
    displayPlaces(places) {
      this.items = places
      // let listEl = document.getElementById('placesList')
      let menuEl = document.getElementById('menu_wrap')
      let bounds = new kakao.maps.LatLngBounds()

      // this.removeAllChildNods(listEl);
      this.removeMarker();
      for (let i = 0; i < places.length; i++) {
        let placePosition = new kakao.maps.LatLng(places[i].y, places[i].x)
        let marker = this.addMarker(placePosition, i)
        bounds.extend(placePosition);

        kakao.maps.event.addListener(marker, 'mouseover', () => {
          this.infowindow.open(this.map, marker)
          this.displayInfowindow(marker, places[i].place_name);
        })

        kakao.maps.event.addListener(marker, 'mouseout', () => {
          this.infowindow.close()
        })
        kakao.maps.event.addListener(marker, 'click', ()=> {
          // console.log(places[i])
          this.selectLocation = places[i]
          this.$emit('sendLocation', this.selectLocation)
        })
      }
      menuEl.scrollTop = 0;
      this.map.setBounds(bounds);
    },
    addMarker(position, idx) {
    let imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
      imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
      imgOptions =  {
        spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
        spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
        offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
      },
      markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
        position: position, // 마커의 위치
        image: markerImage 
      });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers.push(marker);  // 배열에 생성된 마커를 추가합니다

      if (this.selectLocation) {
        kakao.maps.event.addListener(marker, 'mouseover', () => {
          this.infowindow.open(this.map, marker)
          this.displayInfowindow(marker, this.selectLocation.place_name);
        })

        kakao.maps.event.addListener(marker, 'mouseout', () => {
          this.infowindow.close()
        })
      }
      return marker;
    },
    displayPagination(pagination) {
      let paginationEl = document.getElementById('pagination')
      let fragment = document.createDocumentFragment();
      let i

      // 기존에 추가된 페이지번호를 삭제합니다
      while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild (paginationEl.lastChild);
      }
      for (i=1; i<=pagination.last; i++) {
        let el = document.createElement('button');
        el.href = "#";
        el.innerHTML = i;

        if (i===pagination.current) {
          el.className = 'on';
        } else {
          el.onclick = (function(i) {
            return function() {
              pagination.gotoPage(i);
            }
          })(i);
        }
        fragment.appendChild(el);
      }
      paginationEl.appendChild(fragment);
    },
    removeMarker() {
      for ( let i = 0; i < this.markers.length; i++ ) {
        this.markers[i].setMap(null);
      }   
      this.markers = [];
    },
    removeAllChildNods(el) {   
      while (el.hasChildNodes()) {
        el.removeChild (el.lastChild);
      }
    },
    setZoomable(zoomable) {
    // 마우스 휠로 지도 확대,축소 가능여부를 설정합니다
      this.map.setZoomable(zoomable);
    }
  }
};
</script>

<style>
.map_wrap {
  margin-top: 10px;
  margin-bottom: 10px;
}
.map {
  width: 65%;
  height: 40vh;
}
.map-location {
  position: relative;
  z-index: 100;
}
.map-location-btn {
  position: absolute;
  margin: 10px;
  /* z-index: 10000; */
  right : 0;
  top: 0
}
.map-space {
  display: flex;
  width: 100%;
}
.map-info {
  width: 100%;
  overflow: scroll;
  height: 33vh;
}
.map-info-list {
  margin-top: 24px;
}
.map-about {
  width: 35%;
  background-color:  #b29887;;
  padding: 10px;
  min-height: 60px;
}
.map-input {
  width: 100%;
  line-height: 30px;
  border: none;
  background-color: white;
}
.map-input-guide {

  position: relative;
  margin: 24px;
}
.map-input-guide li {
  position: relative;
  font-size: 14px;
  /* overflow: hidden; */
  /* text-overflow: ellipsis; */
}
.info {
  margin-bottom: 10px;
  cursor: pointer;
}
.info h5 {
  font-size: 16px;
}
.info span {
  font-size: 15px;
}
@media screen and (max-width: 768px) {
  .map-space {
    display: block;
    width: 100%;
  }
  .map {
    width: 100%;
    /* height: 50vh; */
  }
  .map-about {
    width: 100%;
    min-height: 20vh;
    background-color: #f0d3c1;
  }
  .map-info {
    width: 100%;
    /* height: 50vh; */
  }
  .map-info-list {
    margin-top: 10px;
  }
  .map-input-guide {
    margin: 10px;
  }
}

</style>