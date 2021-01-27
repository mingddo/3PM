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
        <input id="tags" type="text" placeholder="태그를 입력해주세요" v-model="tag">
      </div>
      <div>
        <label for="file">파일 첨부하기 </label>
        <input id="file" type="file" @change="selectFile">
      </div>
      
      <div>
        <label for="content">내용</label>
        <textarea name="content" placeholder="내용을 입력해주세요" id="content" cols="30" rows="10" v-model="content"></textarea>
      </div>

      <button @click="createFeed">작성하기</button>
    </section>

  </div>
</template>

<script>
import NewsFeedHeader from '../../components/NewsFeed/NewsFeedHeader.vue';

export default {
  name: 'NewsfeedForm',
  components: {
    NewsFeedHeader,
  },
  data() {
    return {
      Category: this.$route.params.Category,
      completed: false,
      content: "",
      file: "",
      tag: "",
    };
  },
  mounted() {
    
  },
  methods: {
    setDefault () {
      if (this.$route.params.type == 'MODI') {
        this.content = this.$route.params.feed.content
        this.tag = this.$route.params.feed.tag
      }
    },
    selectFile (e) {
      let files = e.target.files || e.dataTransfer.files;
      if (!files.lenght)
        this.file = files[0].name
    },
    createFeed () {
      this.completed = true;
      if (this.$route.params.type == 'NEW') {
        // axios create 요청
        alert('새글작성!')
      } else {
        // axios put 요청
        alert('수정완료!')
      }
      this.$router.push({ name: 'NewsfeedDetail' })
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
  padding: 10px;
}
.newsfeed-form-content div {
  margin: 10px;
  /* padding: 10px; */
}
</style>