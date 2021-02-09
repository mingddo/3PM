<template>
  <article class="feed-box">
    <div @click="goToGroup">
      {{ fd.groupName }}
    </div>
    <div class="feed-userprofile-box">
      <img
        src="https://blog.cpanel.com/wp-content/uploads/2019/08/user-01.png"
        alt="유저프로필이미지"
        class="feed-userprofile-img"
        @click="goToProfile"
      >
      <div class="feed-userprofile-content">
        <div>
          <h3 v-if="fd.user.nickname" class="feed-userprofile-name" @click="goToProfile">{{fd.user.nickname}}</h3>
          <h3 v-else class="feed-userprofile-name" @click="goToProfile">anonymous</h3>
        </div>
        
        <div>
          <p>{{ year }} 년 {{ month }} 월 {{ day}} 일 {{ time }} </p>
        </div>
        
      </div>
    </div>

    <div class="feed-content-box" @click="goToDetail">
      <span v-for="(tag, idx) in fd.tag" :key="idx" @click="goToSearchTag(tag)"> <button> {{ tag }} </button></span>
      <div v-if="fd.file">
        <img :src="fd.file" alt="업로드 파일">
      </div>
      
      <div v-else>
        <p v-html="fd.content">
          <!-- {{ fd.content }} -->
        </p>
      </div>

    </div>
    <div class="feed-like-comment-box" @click="goToDetail">
      <span>
        <i class="far fa-thumbs-up"></i>
        좋아요가 <span v-if="fd.likeCnt">{{ fd.likeCnt }}</span> <span v-else> 0</span> 개가 있습니다.
      </span>
      <span>
        <i class="far fa-comment"></i>
        댓글이 <span v-if="fd.commentCnt">{{ fd.commentCnt }}</span> <span v-else> 0</span> 개가 있습니다.
      </span>
    </div>
  </article>
</template>

<script>
export default {
  name: 'FeedForGroup',
  props: {
    fd: Object,
    Category: String,
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
    goToGroup () {
      alert(`id : ${this.fd.groupId}, name: ${this.fd.groupName} 의 그룹 페이지로 이동하겠습니다! `)
      this.$router.push({ name : 'groupdetail'})
    },
    setDateTime () {
      if (this.fd) {
        let date = this.fd.localDateTime.split('T')[0]
        this.time = this.fd.localDateTime.split('T')[1]
        this.year = date.split('-')[0]
        this.month = date.split('-')[1]
        this.day = date.split('-')[2]
      }
    },
    goToProfile () {
      this.$router.push({ name: 'MyPage', query: { name: this.fd.user.id}})
    },
    goToSearchTag (tag) {
      this.$router.push({ name: 'Search', query: { query: tag, filter: 'tag' }})
    },
    goToDetail () {
      this.$router.push({ name: 'groupnewsfeeddetail', query: { id: this.fd.id }, params: {fd: this.fd}})
    }
  },
  mounted () {
    this.setDateTime();
  },
};
</script>

<style>
.feed-userprofile-box {
  display: flex;
}
.feed-userprofile-img {
  cursor: pointer;
  width: 10%;
  height: 10%;
  text-align: left;
}
.feed-userprofile-content {
  margin: 10px;
  /* padding: 10px; */
}
.feed-userprofile-name {
  cursor: pointer;
}
/* .feed-content-box {
  cursor: pointer;
  margin: 0 auto;
  text-align: left;
  padding: 10px;
} */
/* .feed-content-box:hover {
  box-shadow: 0px 5px 10px rgba(0,0,0,0.2);
} */
.feed-like-comment-box {
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  margin: 10px;
  font-size: 20px;
}
</style>