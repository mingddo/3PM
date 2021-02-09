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
