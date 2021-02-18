<template>
  <div class="ActivityComponent">
    <header class="myPageActivityHeader">
      <h2>가입한 그룹 목록</h2>
    </header>
    <div v-if="grouplist.length" class="myPageActivityContentFrame">
      <GroupCard v-for="(group, idx) in grouplist" :key="idx" :group="group"/>
    </div>
    <div v-else class="myPageActivityContentFrame">
      <div class="group-page-card">가입한 그룹이 없습니다.</div>
    </div>
  </div>
</template>

<script>
// import Group from "./Group.vue";
import GroupCard from '../GroupFeed/GroupCard.vue';

export default {
  components: { GroupCard },
  props: {
    grouplist: Array,
  },
};
</script>

<style scoped>
.ActivityComponent {
  width: 100%;
}
.myPageActivityHeader {
  width: 100%;
  margin: auto;
  /* padding: 5px 50px; */
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.myPageActivityhr {
  width: 90%;
}

.myPageActivityHeaderMoreInfo {
  width: 70px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.fa-angle-right {
  font-size: 30px;
}
.myPageActivityContentFrame {
  width: 100%;
  padding: 15px 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  
}

</style>
