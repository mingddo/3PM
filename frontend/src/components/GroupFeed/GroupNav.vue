<template>
  <div class="group-nav">
    <div class="group-nav-content"> 
      <span class="group-nav-btn" :class="{groupNavBtnActive:isHome}" @click="goToGroupFeed">홈 </span>  |
      <span class="group-nav-btn" :class="{groupNavBtnActive:!isHome}" @click="goToGroupPage">그룹</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'GroupNav',
  props: {
    isHome: Boolean,
  },
  data() {
    return {
      
    };
  },
  methods: {
    goToGroupFeed () {
      this.$router.push({ name: 'NewsfeedPersonal', query: { Category : 2}})
    },
    goToGroupPage () {
      this.$router.push({ name: 'grouppage'})
    },
  },
};
</script>

<style>
.group-nav {
  margin: 20px;
  
}
.group-nav-content {
  font-size: 24px;
}

.group-nav-btn {
  font-size: 24px;
  cursor: pointer;
}

.group-nav-btn:hover {
  text-decoration: underline;
}

.groupNavBtnActive {
  color: #f0856a
}
</style>