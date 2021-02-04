<template>
  <div class="newsfeed">
    <NewsFeedHeader
      :Category="Category"
      class="newsfeed-header"
    />
    
    <section class="newsfeed-form-content">
      <h2>피드 작성하기</h2>
      <div>
        <label for="tags">태그</label>
        <input class="newsfeed-form-tag-input" id="tags" type="text" placeholder="태그를 입력해주세요" v-model="inputTag" @keyup.enter="addTag">
      </div>
      <div>
        <span v-for="(tag, idx) in form.tags" :key="idx">
          <button>{{ tag }} <i @click="deleteTag(tag)" class="fas fa-times-circle"></i> </button>
        </span>
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
        <div class="newsfeed-form-img-box">
          <button @click="getLocation">
            <i class="fas fa-map-marker-alt"></i> 현재 위치
          </button>
        </div>
        <div class="newsfeed-form-img-box">
          <button>
            <i class="fas fa-map"></i> 지도
          </button>
        </div>
      </div>     
      <div>
        <label for="content">내용</label>
        <textarea class="newsfeed-form-content-input" name="content" placeholder="내용을 입력해주세요" id="content" cols="30" rows="10" v-model="form.content"></textarea>
      </div>
      <div class="newsfeed-form-submit-btn">
        <button @click="createFeed">작성하기</button>
      </div>
    </section>

  </div>
</template>

<script>
import NewsFeedHeader from '../../components/NewsFeed/NewsFeedHeader.vue';
import { createFeed } from '@/api/feed.js'
import { updateFeed } from '@/api/feed.js'
import { mapState } from 'vuex'

export default {
  name: 'NewsfeedForm',
  components: {
    NewsFeedHeader,
  },
  data() {
    return {
      Category: this.$route.query.Category,
      type: 'NEW',
      completed: false,
      inputTag: "",
      form: {
        tags: [],
        file: null,
        content: null,
        userId: null,
      },
    };
  },
  methods: {
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
      if (this.form.tags) {

        let check = this.form.tags.findIndex(element => element === this.inputTag)
        if (check !== -1) {
          alert('이미 존재하는 태그입니다') 
        } else {
          this.form.tags.push(this.inputTag);
        }
      }
      this.inputTag = '';
    },
    setDefault () {
      if (this.$route.params.type == 'MODI') {
        this.type = 'MODI'
        this.form.content = this.$route.params.feed.content
        this.form.tags = this.$route.params.feed.tag
      } else if (this.$route.params.type == 'SHARE') {
        // let link = document.location.href; 
        this.type = 'SHARE'
        this.form.content = `<b>[공유]</b> <br> <a href="${this.$route.params.link}">원문이동</a> <br> ${this.$route.params.feed.content}`
        this.form.tags = this.$route.params.feed.tag
      }
    },
    selectFile (e) {
      let files = e.target.files || e.dataTransfer.files;
      
      console.log(e.target.files)
      if (files.length)
        this.form.file = files[0]
        console.log(this.form.file)
    },
    async createFeed () {
      const formData = new FormData ();
      formData.append('content', this.form.content)
      formData.append('userId', this.userpk)
      formData.append('file', this.form.file)
      formData.append('tags', this.form.tags)

      this.completed = true;
      if (this.userpk) {
        this.form.userId = this.userpk
        if (this.type == 'NEW' || this.type == 'SHARE') {
          // axios create 요청
          await createFeed (
            formData,
            (res) => {
              
              this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
            },
            (err) => {
              console.log(err)
              console.log(this.form)
            }
          )
        } else {
          // axios put 요청
          await updateFeed (
            this.$route.params.feed.indoorId,
            this.form,
            (res) => {
              this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
            },
            (err) => {
              console.log(err)
            }
          )
        }
      } else {
        alert('로그인된 유저만 글을 작성할 수 있습니다.')
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

</style>