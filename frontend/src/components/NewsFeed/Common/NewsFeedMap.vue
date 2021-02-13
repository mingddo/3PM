<template>
  <div>
    <div id="mapD" class="map"></div>
  </div>
</template>

<script>
export default {
  name: 'NewsFeedMap',
  props: {
    latitude: Number,
    longitude: Number,
  },
  data() {
    return {
      markers: [],
      map: null,
    };
  },
  mounted() {
    window.kakao && window.kakao.maps
      ? this.setMap()
      : this.addKakaoMapScript();
  },
  methods: {
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

      return marker;
    },
    addKakaoMapScript() {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.setMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=4eefdb70288a52331d930637bf0a9161&libraries=services,clusterer,drawing";
      document.head.appendChild(script);
    },
    setMap () {
      let mapContainer = document.getElementById('mapD'), mapOption = {
        center : new kakao.maps.LatLng(this.latitude, this.longitude),
        level: 3
      };
      this.map = new kakao.maps.Map(mapContainer, mapOption)
      let firstPlacePosition = new kakao.maps.LatLng(this.latitude, this.longitude)
      this.addMarker(firstPlacePosition, 0)
      this.map.setZoomable(false);
    },
  },
};
</script>

<style>
#mapD {
  width: 100%;
}
</style>