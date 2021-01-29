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
.feed-list {
  border: 1px solid;
  padding: 20px;
}
.feed-box {
  border: 1px solid;
  padding: 10px;
  margin: 10px;
  border-radius: 10px;
}
.feed-box:hover {
  box-shadow: 0px 5px 10px rgba(0,0,0,0.2);
}
.feed-create-btn-place {
  /* margin-left: auto;0 */
  text-align: right;
}
.feed-create-btn {
  cursor: pointer;
  border: 1px solid;  
  border-radius: 5px;
  padding: 10px;
}
.feed-create-btn:hover {
  box-shadow: 0px 5px 10px rgba(0,0,0,0.2);
}
</style>