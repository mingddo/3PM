<template>
  <section>
    <h2>추천</h2>
    <carousel>
      <slide
        v-for="(rec, idx) in reco"
        :key="idx"
      >
        <Feed
          class="newsfeed-recommend-content"
          :fd="rec"
          :Category="Category"
        />
      </slide>
    </carousel>
  </section>
</template>

<script>
import { Carousel, Slide } from 'vue-carousel';
import Feed from './Feed.vue';
export default {
  name: 'NewsFeedRecommend',
  props: {
    reco: Array,
    Category: String,
  },
  components: {
    Carousel,
    Slide,
    Feed,
  },
  data() {
    return {
      
    };
  },
  mounted() {
    
  },
  methods: {
    
  },
};
</script>

<style lang="scss" scoped>

</style>