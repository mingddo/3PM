<template>
  <div>
    <h1>
      {{ Category }}
    </h1>
    <!-- <div class="newsfeed-header-search">
      <i class="fas fa-search"></i>
      <input type="text" v-model="keyword" @keyup.enter="goToSearch">
    </div> -->
  </div>
</template>

<script>
export default {
  name: 'NewsFeedHeader',
  props : {
    Category: String,
  },
  data() {
    return {
    };
  },
  methods: {
    
  },
};
</script>

<style scoped>

</style>