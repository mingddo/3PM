<template>
  <div class="ActivityComponent">
    <header class="myPageActivityHeader">
      <h2>최근 활동</h2>
    </header>
    <hr class="myPageActivityhr" />
    <div class="myPageActivityContentFrame">
      <MyPageActivity
        v-for="(activity, idx) in activities"
        :key="idx"
        :activity="activity"
      />
    </div>
  </div>
</template>

<script>
import MyPageActivity from "@/components/MyPage/MyPageActivity.vue";

export default {
  components: { MyPageActivity },
  props: {
    activities: Array,
  },
};
</script>

<style scoped>
.ActivityComponent {
  width: 100%;
}
.myPageActivityHeader {
  width: 90%;
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
  width: 90%;
  margin: auto;
  padding: 15px 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>
