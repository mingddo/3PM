<template>
  <section>
    <div>
      <h2>피드</h2>
      <button @click="goToCreateForm">새 글 작성</button>
    </div>
    <div>
      <Feed
        class="newsfeed-recommend-content"
        v-for="(fd, idx) in feed"
        :key="idx"
        :fd="fd"
        :Category="Category"
      />
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
  mounted() {
    
  },
  methods: {
    goToCreateForm () {
      alert('새글 작성하러 가기!')
      this.$router.push({ name: 'NewsfeedForm', query: { Category: this.Category }, params: {type: 'NEW' }})
    },
  },
};
</script>

<style lang="scss" scoped>

</style>