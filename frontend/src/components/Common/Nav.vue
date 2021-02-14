<template>
  <header>
    <!-- <div class="nav">
      <div class="nav-logo" @click="goToHome">
        <i class="far fa-clock"></i>
        오후 세시.
      </div>
      <div class="nav-content" v-if="userStatus">
        <!-- <div @click="goToHome">
          <i class="fas fa-home">홈</i>
        </div> -->
    <!-- <div @click="goToPersonal">
          <i class="fas fa-house-user">꽃보다 집</i>
        </div>
        <div @click="goToSearch">
          <i class="fas fa-search">검색</i>
        </div>
        <div @click="goToProfile">
          <i class="fas fa-user">프로필</i>
        </div>
      </div> -->

    <!-- <button><router-link to="/">홈</router-link></button>
      <button><router-link to="/">핵인싸</router-link></button>
      <button><router-link to="/">청산별곡</router-link></button>
      <button><router-link to="/">만수무강</router-link></button>
      <button>꽃보다집</button>
      <button><router-link to="/">🔍</router-link></button>
      <button @click="goToProfile">프로필</button> -->
    <!-- </div> -->
  </header>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Nav",
  data() {
    return {};
  },
  methods: {
    goToHome() {
      this.$router.push({ name: "Home" });
    },
    goToSearch() {
      this.$router.push({
        name: "Search",
        params: { filter: "all" },
        query: { query: "" },
      });
    },
    goToPersonal() {
      this.$router.push({
        name: "NewsfeedPersonal",
        query: { Category: "꽃보다 집" },
      });
    },
    goToProfile() {
      this.$router.push({ name: "MyPage" });
    },
  },
  computed: {
    ...mapState({
      userStatus: (state) => state.userStatus,
    }),
  },
};
</script>

<style>
.nav {
  display: flex;
  justify-content: space-between;
}
.nav-logo {
  width: 30%;
  padding: 30px;
  font-size: 30px;
  cursor: pointer;
}
.nav-content {
  display: flex;
  /* flex-direction: row; */
  width: 30%;
  margin-right: 150px;
  padding: 30px;
  /* background-color: grey; */
  justify-content: space-evenly;
  font-size: 20px;
}
.nav-content > div {
  cursor: pointer;
  padding: 10px;
  /* border: 1px solid; */
}
.nav-content > div:hover {
  border-radius: 5px;
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.2);
}
</style>
