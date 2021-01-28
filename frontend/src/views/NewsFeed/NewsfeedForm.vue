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
      </div>     
      <div>
        <label for="content">내용</label>
        <textarea class="newsfeed-form-content-input" name="content" placeholder="내용을 입력해주세요" id="content" cols="30" rows="10" v-model="form.content"></textarea>
      </div>

      <button @click="createFeed">작성하기</button>
    </section>

  </div>
</template>

<script>
import NewsFeedHeader from '../../components/NewsFeed/NewsFeedHeader.vue';
import { createFeed } from '@/api/feed.js'
import { updateFeed } from '@/api/feed.js'

export default {
  name: 'NewsfeedForm',
  components: {
    NewsFeedHeader,
  },
  data() {
    return {
      Category: this.$route.query.Category,
      completed: false,
      inputTag: "",
      form: {
        tags: [],
        file: null,
        content: null,
        userId: 4,
      },
    };
  },
  methods: {
    deleteTag (tag) {
      let check = this.form.tags.findIndex(element => element === tag)
      this.form.tags.splice(check, 1)
    },
    addTag () {
      // console.log(this.form.tag.find(element => element === this.inputTag))
      // console.log(this.form.tag.findIndex(element => element === this.inputTag))
      let check = this.form.tags.findIndex(element => element === this.inputTag)
      if (check !== -1) {
        alert('이미 존재하는 태그입니다') 
      } else {
        this.form.tags.push(this.inputTag);
      }
      this.inputTag = '';
    },
    setDefault () {
      if (this.$route.params.type == 'MODI') {
        this.form.content = this.$route.params.feed.content
        this.form.tags = this.$route.params.feed.tag
      } else if (this.$route.params.type == 'SHARE') {
        this.form.content = `[공유] ${this.$route.params.feed.content}`
        this.form.tags = this.$route.params.feed.tag
      }
    },
    selectFile (e) {
      let files = e.target.files || e.dataTransfer.files;
      if (!files.lenght)
        this.file = files[0].name
    },
    async createFeed () {
      this.completed = true;
      if (this.$route.params.type == 'NEW') {
        // axios create 요청
        await createFeed (
          this.form,
          (res) => {
            this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
          },
          (err) => {
            console.log(err)
          }
        )
      } else {
        // axios put 요청
        await updateFeed (
          this.form,
          (res) => {
            console.log(res)
          },
          (err) => {
            console.log(err)
          }
        )
      }
      
    },
  },
  created () {
    this.setDefault();
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
  }
};
</script>

<style>
.newsfeed-form-content {
  border: 1px solid;
  padding: 20px;
}
.newsfeed-form-content div {
  margin: 10px;
  /* padding: 10px; */
}
.newsfeed-form-tag-input {
  width: 90%;
  padding: 10px;
}
.newsfeed-form-content-input {
  width: 90%;
  border-radius: 5px;
}
.newsfeed-form-file-box {
  display: flex;
  justify-content: space-evenly;
}
.newsfeed-form-img-box label {
  display: inline-block; 
  padding: .5em .75em; 
  color: #999; 
  font-size: inherit; 
  line-height: normal; 
  vertical-align: middle; 
  background-color: #fdfdfd; 
  cursor: pointer; 
  border: 1px solid #ebebeb; 
  border-bottom-color: #e2e2e2; 
  border-radius: .25em;
}
.newsfeed-form-img-box input[type="file"] {
  position: absolute; 
  width: 1px; 
  height: 1px; 
  padding: 0; 
  margin: -1px; 
  overflow: hidden; 
  clip:rect(0,0,0,0); 
  border: 0;
}
</style>