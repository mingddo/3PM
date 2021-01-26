<template>
  <article>

    <div class="feed-userprofile-box" @click="goToProfile">
      <img
        src="https://blog.cpanel.com/wp-content/uploads/2019/08/user-01.png"
        alt="유저프로필이미지"
        class="feed-userprofile-img"
      >
      <h3 class="feed-userprofile-name">{{fd.user_id}}</h3>
    </div>

    <div class="feed-content-box">
      <h2 @click="goToSearchTag"> {{fd.tag}} </h2>
      <img @click="goToDetail" :src="fd.file" alt="업로드 파일">
      <p @click="goToDetail">
        {{fd.content}}
      </p>
    </div>
    <div>
      <span>
        <i class="far fa-thumbs-up"></i>
        {{ fd.like_cnt }}
      </span>
      <span>
        <i class="far fa-comment"></i>
        {{ fd.comment_cnt }}
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
      alert(`${this.fd.user_id}의 프로필로 이동할거에요!`)
    },
    goToSearchTag () {
      alert(`${this.fd.tag}의 검색결과로 이동할거에요!`)
    },
    goToDetail () {
      alert(`${this.fd.id}의 상세페이지로 이동할거에요!`)
    }
  },
};
</script>

<style scoped>
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