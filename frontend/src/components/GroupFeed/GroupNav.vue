<template>
  <div class="group-nav">
    <span class="group-nav-content"> 
      <span class="group-nav-btn" :class="{groupNavBtnActive:isHome}" @click="goToGroupFeed">홈 </span>
      <span class="group-nav-btn" :class="{groupNavBtnActive:!isHome}" @click="goToGroupPage">그룹</span>
    </span>
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
      .catch((err) => {
        if (err.name === "NavigationDuplicated") {
          location.reload();
        }
      })
    },
    goToGroupPage () {
      this.$router.push({ name: 'grouppage'})
      .catch((err) => {
        if (err.name === "NavigationDuplicated") {
          location.reload();
        }
      })
    },
  },
};
</script>

<style>
.group-nav {
  /* margin: 20px; */
  margin-bottom: 20px;
}
.group-nav-content {
  padding: 10px;
  font-size: 24px;
  border-radius: 5px;
}

.group-nav-btn {
  font-size: 24px;
  cursor: pointer;
}

.group-nav-btn:hover {
  text-decoration: underline;
}

.groupNavBtnActive {
  color: #b29887;
}
</style>