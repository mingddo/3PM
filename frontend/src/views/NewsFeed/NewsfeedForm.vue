<template>
  <div>
    <Sidebar />
    <div class="newsfeed newsfeed-form">
      <NewsFeedHeader
        :Category="Category"
        class="newsfeed-header"
      />
      
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
        <div>
          <input class="newsfeed-form-tag-input" id="tags" type="text" placeholder="태그를 작성후 엔터를 눌러 태그를 등록해주세요" v-model.trim="inputTag" @keyup.enter="addTag">
        </div>
        <div>
          추가한 태그들 : 
          <span v-for="(tag, idx) in form.tags" :key="idx">
            <button>{{ tag }} <i @click="deleteTag(tag)" class="fas fa-times-circle"></i> </button>
          </span>
        </div>
        <div>
          <i class="far fa-question-circle">태그가 무엇인가요?</i>
          태그를 등록하시면, 검색창에서 해당 태그를 검색시 회원님의 게시물이 나타납니다! 많은 분들과 회원님의 피드를 나누고 싶다면? ... 태그 설명
          
        </div>
        <div>
          <!-- <label for="content">내용</label> -->
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
            <button @click="getLocation">
              <i class="fas fa-map-marker-alt"></i> 현재 위치
            </button>
          </div>
          <div class="newsfeed-form-img-box" v-if="Category == 2 || Category == 3">
            <button>
              <i class="fas fa-map"></i> 지도
            </button>
          </div>
        </div>
        <div class="newsfeed-form-img">
          <div v-for="(view, idx) in previewUrl" :key="idx">
            <div class="newsfeed-form-img-prevbox">
              <!-- <i class="fas fa-times-circle newsfeed-form-img-prevbox-delbtn"></i> -->
              <img class="newsfeed-form-img-prevbox-delbtn" src="https://img.icons8.com/fluent/20/000000/close-window.png"/>
              <!-- <i class="far fa-times-circle  newsfeed-form-img-prevbox-delbtn"></i> -->
              <img :src="view" alt="미리보기 이미지" width="100%">
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

export default {
  name: 'NewsfeedForm',
  components: {
    NewsFeedHeader,
    Sidebar,
  },
  data() {
    return {
      Category: 'null',
      type: 'NEW',
      completed: false,
      select: false,
      inputTag: "",
      form: {
        tags: [],
        file: null,
        content: null,
      },
      imageUrl: ``,
      previewUrl : [],
      fileList: [],
      groupName: "그룹을 선택해주세요",
      groupList: [],
    };
  },
  methods: {
    selectGroup (group) {
      this.groupName = group
      this.select = false
    },
    chooseGroup () {
      this.select = !this.select
    },
    getLocation () {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition (function(position) {
          alert(`위도는${ position.coords.latitude } 경도는 ${ position.coords.longitude } `);
        }, function(error) {
          console.log(error);
        }, {
          enableHighAccuracy: false,
          maximumAge: 0,
          timeout: Infinity
        });
      } else {
        alert('GPS를 지원하지 않습니다.')
      }
    },
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
      this.fileList.push(files.[0])
      console.log(this.fileList)
      if (files.length) {
        this.form.file = files[0]
        console.log(this.form.file)
        // this.imageUrl = URL.createObjectURL(this.form.file);
        // console.log(this.imageUrl)
        let reader = new FileReader(); 
        reader.onload = (e) => {
          // console.log(e.target.result)
          this.imageUrl = e.target.result;
          this.previewUrl.push(this.imageUrl)
        } 
        reader.readAsDataURL(this.form.file);
      }
    },
    async createFeed () {

      const formData = new FormData ();
      formData.append('content', this.form.content)
      formData.append('file', this.fileList)
      formData.append('tags', this.form.tags)
      this.completed = true;
      if (this.type == 'NEW' || this.type == 'SHARE') {
        // axios create 요청
        if (this.Category == 1) {
          await createFeed (
            formData,
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
            formData.append('groupName', this.groupName)
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
            await updateFeed (
              this.$route.params.feed.id,
              formData,
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
          alert('본인만 수정할 수 있습니다.')
        }
      }
    },
  },
  created () {
    this.setDefault();
    // this.getLocation();
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

<style>
.newsfeed-form-img {
  display: flex;
  flex-direction : row;
  flex-wrap : wrap;
}
.newsfeed-form-img-prevbox-delbtn {
  margin: 5px;
  top: 0;
  right: 0;
  position: absolute;
  z-index: 2;
  color: red;
  cursor: pointer;
}
.newsfeed-form-img-prevbox-delbtn:hover {
  animation: shake 0.82s cubic-bezier(.36,.07,.19,.97) both;
  transform: translate3d(0, 0, 0)
}
.newsfeed-form-img-prevbox {
  width: 100px;
  height: 100px;
  border: 1px dashed;
  margin: 20px;
  position: relative;

}
</style>