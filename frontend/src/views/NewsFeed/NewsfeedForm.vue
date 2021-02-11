<template>
  <div>
    <Sidebar />
      <div class="newsfeed-header" >
        <NewsFeedHeader :Category="Number(Category)" />
      </div>
    <div class="newsfeed newsfeed-form">
      <section class="newsfeed-form-content">
        <div v-if="Category == 2">
          <div @click="chooseGroup">{{groupName}} </div>
          <div v-if="select">
            <!-- <input type="text"> -->
            <div v-for="(group, idx) of groupList" :key="idx">
              <button @click="selectGroup(group)"> {{ group }} </button>
            </div>
          </div>
        </div>

        <div class="newsfeed-form-profile">
          <div class="newsfeed-form-profile-img-space">
            <img
              src="http://image.yes24.com/momo/TopCate2600/MidCate6/259955881.jpg"
              alt="유저프로필이미지"
              class="newsfeed-form-profile-img"
            >
          </div>
          <div class="newsfeed-form-profile-name">
            username
          </div>
        </div>

        <div class="newsfeed-form-tag-input-container">
          <input class="newsfeed-form-tag-input" id="tags" type="text" placeholder="태그를 작성후 엔터를 눌러 태그를 등록해주세요" v-model.trim="inputTag" @keyup.enter="addTag">
          <div style="font-size : 14px; padding-left : 10px; padding-right : 10px;">
          <i class="far fa-question-circle"></i>태그가 무엇인가요? : 게시물을 표현할 수 있는 짧은 단어를 뜻합니다. 태그를 입력하시면 검색시 회원님의 게시물이 나타납니다.
          </div>     
        </div>

        <div class="newsfeed-form-tag-container">
          <span v-for="(tag, idx) in form.tags" :key="idx">
            <button>{{ tag }} <i @click="deleteTag(tag)" class="fas fa-times-circle"></i> </button>
          </span>
        </div>
        <div class="newsfeed-form-content-input-container">
          <textarea class="newsfeed-form-content-input" name="content" placeholder="요즘 어떤 활동을 하고 계신가요?" id="content" cols="30" rows="10" v-model="form.content"></textarea>
        </div>

        <div class="newsfeed-form-file-box">
          <div class="newsfeed-form-img-box">
            <label for="image"> <i class="far fa-images"></i> 사진 </label>
            <input class="newsfeed-form-img-input" id="image" type="file" @change="selectFile" accept="image/*">
          </div>
          <div class="newsfeed-form-img-box">
            <label for="video"> <i class="fas fa-video"></i> 동영상 </label>
            <input class="newsfeed-form-img-input" id="video" type="file" @change="selectFile" accept="video/*">
          </div>
          <div class="newsfeed-form-img-box" v-if="Category == 2 || Category == 3">
            <div class="newsfeed-form-input-map" @click="revealMap">
              <i class="fas fa-map"></i> 지도
            </div>
          </div>
        </div>
        <div v-if="showMap">
          <inputmap @sendLocation="sendLocation"/>
            <div v-if="location">
              {{ location.place_name }}
              {{ location.address_name }}
            </div>
        </div>
        <div class="newsfeed-form-img">
          <div v-for="(view, idx) in previewUrl" :key="idx">
            <div class="newsfeed-form-img-prevbox">
              <!-- <i class="fas fa-times-circle newsfeed-form-img-prevbox-delbtn"></i> -->
              <img class="newsfeed-form-img-prevbox-delbtn" src="https://img.icons8.com/fluent/20/000000/close-window.png"/>
              <!-- <i class="far fa-times-circle  newsfeed-form-img-prevbox-delbtn"></i> -->
              <img :src="view" alt="미리보기 이미지">
            </div>
            
          </div>
          <div class="newsfeed-form-img-prevbox">
            사진
          </div>
        </div>
        <div class="newsfeed-form-submit-btn">
          <button @click="createFeed">작성하기</button>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import NewsFeedHeader from '../../components/NewsFeed/NewsFeedHeader.vue';
import { createFeed } from '@/api/feed.js'
import { updateFeed } from '@/api/feed.js'
import { mapState } from 'vuex'
import Sidebar from '../../components/Common/Sidebar.vue';
import Inputmap from '../../components/NewsFeed/inputmap.vue';

export default {
  name: 'NewsfeedForm',
  components: {
    NewsFeedHeader,
    Sidebar,
    Inputmap,
  },
  data() {
    return {
      Category: 0,
      type: 'NEW',
      completed: false,
      select: false,
      inputTag: "",
      form: {
        tags: [],
        filePaths: [],
        content: "",
      },
      selectedFile: "",
      imageUrl: ``,
      previewUrl : [],
      fileList: [],
      groupName: "그룹을 선택해주세요",
      groupList: [],
      location: null,
      showMap: false,
    };
  },
  methods: {
    sendLocation (place) {
      // this.showMap = false
      this.location = place
    },
    revealMap () {
      this.showMap = !this.showMap;
    },
    selectGroup (group) {
      this.groupName = group
      this.select = false
    },
    chooseGroup () {
      this.select = !this.select
    },
    // getLocation () {
    //   if (navigator.geolocation) {
    //     navigator.geolocation.getCurrentPosition (function(position) {
    //       // this.x = position.coords.latitude
    //       alert(`위도는${ position.coords.latitude } 경도는 ${ position.coords.longitude } `);
    //     }, function(error) {
    //       console.log(error);
    //     }, {
    //       enableHighAccuracy: false,
    //       maximumAge: 0,
    //       timeout: Infinity
    //     });
    //   } else {
    //     alert('GPS를 지원하지 않습니다.')
    //   }
    // },
    deleteTag (tag) {
      let check = this.form.tags.findIndex(element => element === tag)
      this.form.tags.splice(check, 1)
    },
    addTag () {
      if (!this.inputTag) {
        alert('태그를 입력해주세요!')
      } else {
        if (this.form.tags) {

          let check = this.form.tags.findIndex(element => element === this.inputTag)
          if (check !== -1) {
            alert('이미 존재하는 태그입니다') 
          } else {
            this.form.tags.push(this.inputTag);
          }
        }
      }
      this.inputTag = '';
    },
    setDefault () {
      this.Category = this.$route.query.Category
      if (this.Category == 2) {
        this.groupList = ['testGroup', 'testGroup2', 'testGroup3']
      }
      if (this.$route.params.type == 'MODI') {
        this.type = 'MODI'  
        this.form.content = this.$route.params.feed.content
        this.form.tags = this.$route.params.feed.tags
        if (this.$route.params.feed.file) {
          this.imageUrl = `https://dtbqjjy7vxgz8.cloudfront.net/${this.$route.params.feed.file}`
        }  
      } else if (this.$route.params.type == 'SHARE') {
        // let link = document.location.href; 
        this.type = 'SHARE'
        if (this.$route.params.feed.file) {
          this.imageUrl = `https://dtbqjjy7vxgz8.cloudfront.net/${this.$route.params.feed.file}`
        }
        this.form.file = this.$route.params.feed.file
        this.form.content = `<b>[공유]</b> <br> <a href="${this.$route.params.link}">원문이동</a> <br> ${this.$route.params.feed.content}`
        this.form.tags = this.$route.params.feed.tags
      }
    },
    selectFile (e) {
      let files = e.target.files || e.dataTransfer.files;
      this.form.filePaths.push(files.[0])
      // this.fileList.push(files.[0])
      // console.log(this.fileList)
      if (files.length) {
        this.selectedFile = files[0]
        console.log(this.selectedFile)
        // this.imageUrl = URL.createObjectURL(this.form.file);
        // console.log(this.imageUrl)
        let reader = new FileReader(); 
        reader.onload = (e) => {
          // console.log(e.target.result)
          this.imageUrl = e.target.result;
          this.previewUrl.push(this.imageUrl)
        } 
        reader.readAsDataURL(this.selectedFile);
      }
    },
    async createFeed () {

      // const formData = new FormData ();
      // formData.append('content', this.form.content)
      // formData.append('file', this.fileList)
      // formData.append('tags', this.form.tags)
      // console.log(formData)
      this.completed = true;
      if (this.type == 'NEW' || this.type == 'SHARE') {
        // axios create 요청
        if (this.Category == 1) {
          await createFeed (
            this.form,
            (res) => {
              this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
            },
            (err) => {
              console.log(err)
              alert('인증된 유저만 작성 가능합니다.')
            }
          )
        } else if (this.Category == 2) {
          if (this.GroupName == "그룹을 선택해주세요") {
            alert('그룹을 선택해주세요!')
          } else {
            // 핵인싸 create 요청
            // formData.append('groupName', this.groupName)
          }
        } else if (this.Category == 3) {
          // 청산별곡 create 요청
        } else if (this.Category == 4) {
          // 워커홀릭 create 요청
        } else {
          // 404 페이지 이동
          alert('잘못된 접근입니다.')
        }
      } else {
        // axios put 요청
        if (this.userpk == this.$route.params.feed.user.id) {
          if (this.Category == 1) {
            console.log(this.$route.params.feed.id)
            await updateFeed (
              this.$route.params.feed.id,
              this.form,
              (res) => {
                this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
              },
              (err) => {
                console.log(err)
                alert('본인만 수정할 수 있습니다.')
              }
            )
          } else if (this.Category == 2) {
            // 핵인싸 put 요청
          } else if (this.Category == 3) {
            // 청산별곡 put 요청
          } else if (this.Category == 4) {
            // 워커홀릭 put 요청
          } else {
            // 404 페이지 이동
            alert('잘못된 접근입니다.')
          }
        } else {
          alert('본인만 수정할 수 있습니다!!!')
        }
      }
    },
  },
  created () {
    this.setDefault();
    // this.getLocation();
  },
  mounted () {
    console.log(this)
  },
  beforeRouteLeave (to, from, next) {
    if (this.completed) {
      next();
    } else {
      const answer = 
        window.confirm('작성 중인 내용이 저장되지 않았습니다. 화면을 나가시겠습니까?')
      if (answer) {
        next();
      } else {
        next(false);
      }
    }
  },
  computed: {
    ...mapState({
      userpk : (state) => state.userId,
    })
  },
};
</script>

<style scoped>
.newsfeed {
  position: absolute;
  top : 80px;
}
button {
  background-color : #f9e7e7;
}
</style>