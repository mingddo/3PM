<template>
  <div>
    <h1>
      핵인싸
    </h1>
    <div>
      <span @click="goToGroupHome">Home</span> |
      <span @click="goToGroupPage">Group</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'GroupFeedHeader',
  data() {
    return {
    };
  },
  methods: {
    goToGroupHome () {
      this.$router.push({name: 'groupnewsfeed'})
    },
    goToGroupPage () {
      this.$router.push({name: 'grouppage'})
    },
  },
};
</script>

<style scoped>

</style>