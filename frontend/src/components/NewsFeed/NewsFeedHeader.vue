<template>
  <div>
    <h1>
      {{ Category }}
    </h1>
    <div class="newsfeed-header-search">
      <i class="fas fa-search"></i>
      <input type="text">
    </div>
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
  mounted() {
    
  },
  methods: {
    
  },
};
</script>

<style scoped>

</style>