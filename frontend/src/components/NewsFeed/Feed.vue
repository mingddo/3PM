<template>
  <article>

    <div class="feed-userprofile-box">
      <img
        src="https://blog.cpanel.com/wp-content/uploads/2019/08/user-01.png"
        alt="유저프로필이미지"
        class="feed-userprofile-img"
        @click="goToProfile"
      >
      <h3 class="feed-userprofile-name" @click="goToProfile">{{fd.user.nickname}}</h3>
    </div>

    <div class="feed-content-box">
      <h2> | <span v-for="(tag, idx) in fd.tag" :key="idx" @click="goToSearchTag(tag)">{{ tag }} | </span> </h2>

      <div v-if="fd.file">
        <img @click="goToDetail" :src="fd.file" alt="업로드 파일">
      </div>

      <div v-else>
        <p @click="goToDetail">
          {{ fd.content }}
        </p>
      </div>

    </div>
    <div>
      <span>
        <i class="far fa-thumbs-up"></i>
        {{ fd.like.length }}
      </span>
      <span>
        <i class="far fa-comment"></i>
        {{ fd.comment.length }}
      </span>
    </div>
  </article>
</template>

<script>
export default {
  name: 'Feed',
  props: {
    fd: Object,
    Category: String,
  },
  data() {
    return {
      
    };
  },
  methods: {
    goToProfile () {
      this.$router.push({ name: 'MyPage', query: { name: this.fd.user.nickname}})
    },
    goToSearchTag (tag) {
      this.$router.push({ name: 'Search', query: { query: tag, filter: 'tag' }})
    },
    goToDetail () {
      this.$router.push({ name: 'NewsfeedDetail', query: { id: this.fd.id }, params: {Category: this.Category, fd: this.fd}})
    }
  },
};
</script>

<style>
.feed-userprofile-box {
  display: flex;
}
.feed-userprofile-img {
  width: 10%;
  height: 10%;
}
.feed-userprofile-name {
  vertical-align: text-bottom;
}
.feed-content-box {
  margin: 0 auto;
  text-align: center;
}
</style>