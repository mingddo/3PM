<template>
  <div class="ActivityComponent">
    <header class="myPageActivityHeader">
      <h2>알림</h2>
    </header>

    <div v-if="activities" class="myPageActivityContentFrame">
      <MyPageActivity
        v-for="(activity, idx) in activities"
        :key="idx"
        :activity="activity"
      />
    </div>

    <div v-if="activitiesLast" class="feed-last">
      <h3>
        모든 알림을 확인했습니다.
      </h3>
    </div>
    <div class="spinner-container" v-else :class="{ loading: !next }">
      <img
        class="spinner"
        src="https://img.icons8.com/material-two-tone/96/000000/spinner--v3.png"
      />
    </div>
  </div>
</template>

<script>
import MyPageActivity from "@/components/MyPage/MyPageActivity.vue";

export default {
  components: { MyPageActivity },
  props: {
    activities: [Array, String],
    activitiesLast : Boolean,
  },
};
</script>
