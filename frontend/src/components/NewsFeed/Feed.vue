<template>
  <article class="feed-box">    
    <div class="feed-userprofile-box">
      <NewsFeedProfile 
        :proImg="fd.user.img !== null ? fd.user.img : defaultImg"
      />
      
      <div class="feed-userprofile-content">
        <div>
          <p v-if="fd.user.nickname" class="feed-userprofile-name" @click="goToProfile">{{fd.user.nickname}}</p>
          <h3 v-else class="feed-userprofile-name" @click="goToProfile">anonymous</h3>
          <div class="feed-date">
            <!-- <p>{{ year }}년 {{ month }}월 {{ day}}일 {{ time }} </p> -->
            {{agoDate != '0일전' ? agoDate : '오늘'}}
          </div>
        </div>
        <div class="feed-tag-box" v-if="fd.tags.length > 0">
          <div>
            <span class="feed-tag" @click="goToSearchTag(fd.tags[0])">
              <button>{{ fd.tags[0] }}</button>
            </span>
            <span v-if="(fd.tags.length - 1) > 0" class="feed-tag-plus">
              <button @click="goToDetail">
                + {{ fd.tags.length - 1}}
              </button>
            </span>
          </div>
          <!-- <div v-for="(tag, idx) in fd.tags" :key="idx" @click="goToSearchTag(tag)"> <button> {{ tag }} </button></div> -->
        </div>    
      </div>
    </div>

    <div class="feed-content-box">
      <div v-if="fd.files.length != 0" @click="goToDetail">
        <div v-for="(file, idx) in fd.files" :key="idx">
          <img :src="`https://dtbqjjy7vxgz8.cloudfront.net/${file}`" alt="업로드 파일">
        </div>
      </div>
      <div v-else  @click="goToDetail">
        <p v-html="content">
          <!-- {{ fd.content }} -->
        </p>
      </div>

      <div v-if="Category == 2 || Category == 3">
        {{ placeName !== null ? placeName : '위치 정보 없음' }}
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
import { mapState } from 'vuex'
import NewsFeedProfile from './Common/NewsFeedProfile.vue';
export default {
  components: { NewsFeedProfile },
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
      placeName: '대전광역시 유성구 봉명동',
      agoDate: null,
      content: null,
    };
  },
  methods: {
    setComment () {
      this.content = this.fd.content
      this.content = this.content.replace(/(\n|\r\n)/g, '<br>')
    },
    setDateTime () {
      let d = new Date ();
      let todayDate = d.getDate();
      let todayMonth = d.getMonth() + 1;
      console.log(todayMonth)
      if (this.fd) {
        let date = this.fd.date.split('T')[0]
        this.time = this.fd.date.split('T')[1]
        this.year = date.split('-')[0]
        this.month = date.split('-')[1]
        this.day = date.split('-')[2]
        if (this.month == todayMonth) {
          let prevDay = todayDate - this.day
          if (prevDay < 7) {
            this.agoDate = `${prevDay}일전`
          } else if (prevDay < 14){
            this.agoDate = `1주전`
          } else if (prevDay < 21) {
            this.agoDate = '2주전'
          } else if (prevDay < 28) {
            this.agoDate = '3주전'
          } else {
            this.agoDate = '4주전'
          }
        } else {
          let prevMon  = todayMonth - this.month
          if (todayMonth < this.month) {
            prevMon = 12 - this.month + todayMonth
          }
          if (prevMon < 4) {
            this.agoDate = `${prevMon}개월전`
          } else {
            this.agoDate = `수개월전`
          }
          
        }
      }
    },
    goToProfile () {
      this.$router.push({ name: 'MyPage', query: { name: this.fd.user.id}})
    },
    goToSearchTag (tag) {
      this.$router.push({ name: 'Search', query: { query: tag}, params: { filter: "feed" }})
    },
    goToDetail () {
      this.$router.push({ name: 'NewsfeedDetail', query: { id: this.fd.id, Category: this.Category }, params: {fd: this.fd}})
    }
  },
  mounted () {
    this.setDateTime();
    this.setComment();
  },
  computed: {
    ...mapState({
      defaultImg: (state) => state.defaultImg,
    })
  }
};
</script>

<style>

</style>