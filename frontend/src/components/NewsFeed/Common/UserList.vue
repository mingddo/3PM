<template>
  <div class="newsfeed-like-list">
    <div class="newsfeed-like-list-header">
      <h2>{{ title }} </h2>
    </div>

    <div class="newsfeed-like-list-body">
      <div class="newsfeed-like-list-user" v-for="(user, idx) in users" :key="idx">
        <div class="newsfeed-like-list-userProfile">
          <NewsFeedProfile :proImg="user.img? user.img : user.profileImg"/>
          {{ user.nickname }}
        </div>
        <div class="newsfeed-like-list-btn">
          <button @click="goToProfile(user)">
            프로필로 이동하기
          </button>
        </div>
      </div>
    </div>

    <div class="newsfeed-like-list-footer">
      <button class="newsfeed-like-list-btnClose" @click="closeList">닫기</button>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import NewsFeedProfile from './NewsFeedProfile.vue';
export default {
  components: { NewsFeedProfile },
  name: 'NewsFeedClapUser',
  props: {
    users: Array,
    type: Number,
  },
  data() {
    return {
      title: '',
    };
  },
  mounted() {
    this.setTitle();
  },
  methods: {
    setTitle () {
      if (this.type == 1) {
        this.title = '이 글에 👏박수를 친 사람들'
      } else if (this.type == 2) {
        this.title = '그룹 멤버'
      } else if (this.type == 3) {
        this.title = '이 댓글에 👏박수를 친 사람들'
      } else if (this.type == 4) {
        this.title = '나를 구독한 사람들'
      }
    },
    closeList () {
      this.$emit('closeList')
    },
    goToProfile (user) {
      this.$router.push({ name: 'MyPage', query: { name: user.id}})
    },
  },
  computed: {
    ...mapState({
      defaultImg: (state) => state.defaultImg,
    }),
  },
};
</script>

<style>
.newsfeed-like-list {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 700;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.newsfeed-like-list-header {
  text-align: center;
  padding: 10px;
  /* border-bottom: 5px double; */
  background-color: white;
  border-radius: 20px;
  /* box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.2); */
  /* background-color: rgba(0,0,0,0.2); */
}
.newsfeed-like-list-close {
  cursor: pointer;
  font-size: 30px;
}
.newsfeed-like-list-body {
  background-color: white;
  margin-top: 10px;
  overflow: auto;
  height: 60vh;
  width: 80vh;
  border-radius: 10px;
}
.newsfeed-like-list-user {
  padding: 10px;
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid;
}
.newsfeed-like-list-userProfile {
  display: flex;
  align-items: center;
}
.newsfeed-like-list-footer {
  width: 30vh;
  text-align: center;
  margin-top:10px;
}
.newsfeed-like-list-btn {
  margin: auto;
  margin-right: 0;
}
.newsfeed-like-list-btnClose {
  width: 100%;
  z-index: 102;
  background-color: white;
}
@media screen and (max-width: 900px) {
  .newsfeed-like-list-body {
    width: 100%;
    height: 60vh;
  }
}
</style>