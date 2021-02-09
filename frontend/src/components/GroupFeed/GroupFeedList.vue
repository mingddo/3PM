<template>
  <section class="feed-list">
    <div>
      <h2>피드</h2>
    </div>
    <div class="feed-create-btn-place">
      <i class="fas fa-plus feed-create-btn" @click="goToCreateForm">새 피드 작성</i> 
    </div>
    <div v-if="feed">
      <FeedForGroup 
        v-for="(fd, idx) in feed"
        :key="idx"
        :fd="fd"
      />
    </div>
    <div v-else>
      <h1>아직 피드가 없습니다</h1>
      <h1>새로 작성해주세요!</h1>
    </div>
    <div v-if="last" class="feed-last">
      <img src="https://img.icons8.com/ios/100/000000/sad-sun.png"/>
      <h3>
        모든 피드를 확인했습니다. 새로운 피드를 작성해주세요.
      </h3>
      <i class="fas fa-plus feed-create-btn" @click="goToCreateForm">새 피드 작성</i> 
    </div>
    <div v-else class="loading">
      <img class="spinner" src="https://img.icons8.com/material-two-tone/96/000000/spinner--v3.png"/>
    </div>
  </section>
</template>

<script>
import FeedForGroup from './FeedForGroup.vue';
export default {
  components: { FeedForGroup },
  name: 'GroupFeedList',
  props: {
    feed: Array,
    Category: String,
    last: Boolean,
  },
  data() {
    return {
      
    };
  },
  methods: {
    goToCreateForm () {
      this.$router.push({ name: 'groupnewsfeedform', params: {type: 'NEW' }})
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
.feed-last {
  text-align: center;
  margin: auto;
}
</style>