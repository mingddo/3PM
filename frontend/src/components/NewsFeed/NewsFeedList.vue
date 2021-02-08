<template>
  <section class="feed-list-frame">
    <div class="feed-create-btn-place">
      <i class="fas fa-plus feed-create-btn" @click="goToCreateForm"
        >글쓰기</i
      >
    </div>
    <div v-if="feed">
      <Feed
        class="feed-box"
        v-for="(fd, idx) in feed"
        :key="idx"
        :fd="fd"
        :Category="Category"
      />
    </div>
    <div v-else>
      <h1>아직 피드가 없습니다</h1>
      <h1>새로 작성해주세요!</h1>
    </div>
    <div v-if="last" class="feed-last">
      <img src="https://img.icons8.com/ios/100/000000/sad-sun.png" />
      <h3>
        모든 피드를 확인했습니다. 새로운 피드를 작성해주세요.
      </h3>
      <i class="fas fa-plus feed-create-btn" @click="goToCreateForm"
        >새 피드 작성</i
      >
    </div>
    <div class="spinner-container" v-else :class="{ loading: !next }">
      <img
        class="spinner"
        src="https://img.icons8.com/material-two-tone/96/000000/spinner--v3.png"
      />
    </div>
  </section>
</template>

<script>
import Feed from "./Feed.vue";

export default {
  components: { Feed },
  name: "NewsFeedList",
  props: {
    feed: Array,
    Category: Number,
    last: Boolean,
    next: Boolean,
  },
  data() {
    return {};
  },
  methods: {
    goToCreateForm() {
      this.$router.push({
        name: "NewsfeedForm",
        query: { Category: this.Category },
        params: { type: "NEW" },
      });
    },
  },
  watch: {},
};
</script>

<style>
</style>
