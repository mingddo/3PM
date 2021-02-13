<template>
  <div class="activityComponentFrame">
    <div class="profileImgFrame">
      <img class="pofileImg" src="@/assets/loverduck.png" alt="프로필사진" />
    </div>
    <div class="activityContent">
      <div>{{ activity.dayInfo }}</div>
      <div>{{ activity.content }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    activity: Object,
  },
  mounted() {
    console.log(this.activity);
  },
};
</script>