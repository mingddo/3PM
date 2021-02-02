<template>
  <section class="feed-list">
    <div>
      <h2>피드</h2>
    </div>
    <div class="feed-create-btn-place" @click="goToCreateForm">
      <i class="fas fa-plus feed-create-btn">새 피드 작성</i> 
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
  </section>
</template>

<script>
import Feed from './Feed.vue';

export default {
  components: { Feed },
  name: 'NewsFeedList',
  props: {
    feed: Array,
    Category: String,
  },
  data() {
    return {
      
    };
  },
  methods: {
    goToCreateForm () {
      this.$router.push({ name: 'NewsfeedForm', query: { Category: this.Category }, params: {type: 'NEW' }})
    },
  },
  watch: {},
};
</script>

<style>

</style>