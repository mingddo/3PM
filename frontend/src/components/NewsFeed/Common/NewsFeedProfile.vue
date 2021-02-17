<template>
  <div class="feed-userprofile-space" @click="goToProfile">
    <img :src="`https://dtbqjjy7vxgz8.cloudfront.net/${proImg}`" alt="프로필이미지" class="feed-userprofile-image">
  </div>
</template>

<script>
export default {
  name: 'NewsFeedProfile',
  props : {
    proImg: String,
    userId: Number,
  },
  data() {
    return {
      
    };
  },
  mounted() {
    
  },
  methods: {
    goToProfile () {
      this.$router.push({ name: 'MyPage', query: { name: this.userId}})
      .catch((err) => {
        if (err.name === "NavigationDuplicated") {
          location.reload();
        }
      })
    },
  },
};
</script>

<style lang="scss" scoped>

</style>