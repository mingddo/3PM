<template>
  <div class="newsfeed-like-list">
    <div class="newsfeed-like-list-header">
      <h2> 이 글에 👏박수를 친 사람들</h2>
    </div>

    <div class="newsfeed-like-list-body">
      <div class="newsfeed-like-list-user" v-for="(user, idx) in clapedUsers" :key="idx">
        <div class="newsfeed-like-list-userProfile">
          <NewsFeedProfile
            :proImg="user.img !== null ? user.img : defaultImg"
          />
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
      <button class="newsfeed-like-list-btnClose" @click="closeClapList">닫기</button>
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
    clapedUsers: Array,
  },
  data() {
    return {
      
    };
  },
  mounted() {
    
  },
  methods: {
    closeClapList () {
      this.$emit('closeClapList')
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
  top: 0;
  background-color: white;
  padding: 5px;
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 100;
}
.newsfeed-like-list-header {
  text-align: center;
  padding: 10px;
  border-bottom: 5px double;
  /* box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.2); */
  /* background-color: rgba(0,0,0,0.2); */
}
.newsfeed-like-list-close {
  cursor: pointer;
  font-size: 30px;
}
.newsfeed-like-list-body {
  margin-top: 10px;
  overflow: scroll;
  height: 80vh;
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
  width: 100%;
  text-align: center;
  position: sticky;
  bottom: 0;
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
</style>