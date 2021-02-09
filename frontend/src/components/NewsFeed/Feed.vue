<template>
  <article class="feed-box">    
    <div class="feed-userprofile-box">
      <div class="feed-userprofile-space">
        <img
          v-if="fd.user.img"
          :src="`https://dtbqjjy7vxgz8.cloudfront.net/${fd.user.img}`"
          onerror="this.src='http://image.yes24.com/momo/TopCate2600/MidCate6/259955881.jpg'"
          alt="유저프로필이미지"
          class="feed-userprofile-image"
          @click="goToProfile"
        >
        <img
          v-else
          src="http://image.yes24.com/momo/TopCate2600/MidCate6/259955881.jpg"
          alt="유저프로필이미지"
          class="feed-userprofile-image"
          @click="goToProfile"
        >
      </div>
      
      <div class="feed-userprofile-content">
        <div>
          <p v-if="fd.user.nickname" class="feed-userprofile-name" @click="goToProfile">{{fd.user.nickname}}</p>
          <h3 v-else class="feed-userprofile-name" @click="goToProfile">anonymous</h3>
          <div class="feed-date">
          <p>{{ month }}.{{ day}} {{ time }} </p>
          </div>
        </div>
        <div class="feed-tag-box">
          <div v-for="(tag, idx) in fd.tags" :key="idx" @click="goToSearchTag(tag)"> <button> {{ tag }} </button></div>
        </div>    
      </div>
    </div>
    <div class="feed-content-box">
      <div v-if="fd.file" @click="goToDetail">
        <img :src="`https://dtbqjjy7vxgz8.cloudfront.net/${fd.file}`" alt="업로드 파일">
      </div>
      <div v-else  @click="goToDetail">
        <p v-html="fd.content">
          <!-- {{ fd.content }} -->
        </p>
      </div>
    </div>
      <div class="feed-footer">
        <div class="feed-footer-box" @click="goToDetail">
            <div>
              <i class="far fa-thumbs-up"></i>
              <span v-if="fd.likeCnt">{{ fd.likeCnt }}</span> <span v-else> 0</span>
            </div>
            <div>
              <i class="far fa-comment"></i>
              <span v-if="fd.commentCnt">{{ fd.commentCnt }}</span> <span v-else> 0</span>
            </div>
        </div>
      </div>
  </article>
</template>

<script>
export default {
  name: 'Feed',
  props: {
    fd: Object,
    Category: Number,
  },
  data() {
    return {
      year: 0,
      month: 0,
      day: 0,
      time: null,
    };
  },
  methods: {
    setDateTime () {
      if (this.fd) {
        let date = this.fd.date.split('T')[0]
        this.time = this.fd.date.split('T')[1]
        this.year = date.split('-')[0]
        this.month = date.split('-')[1]
        this.day = date.split('-')[2] 
        // this.fd.file = "http://image.yes24.com/momo/TopCate2600/MidCate6/259955881.jpg"
        // this.fd.file = "http://image.yes24.com/momo/TopCate2600/MidCate6/259955881.jpg"
      }
    },
    goToProfile () {
      this.$router.push({ name: 'MyPage', query: { name: this.fd.user.id}})
    },
    goToSearchTag (tag) {
      this.$router.push({ name: 'Search', query: { query: tag, filter: 'tag' }})
    },
    goToDetail () {
      this.$router.push({ name: 'NewsfeedDetail', query: { id: this.fd.id, Category: this.Category }, params: {fd: this.fd}})
    }
  },
  mounted () {
    this.setDateTime();
  },
};
</script>

<style>

</style>