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

<style scoped>
.activityComponentFrame {
  display: flex;
  justify-content: flex-start;
  align-items: flex-end;
  margin: 10px 0;
}

.profileImgFrame {
  width: 70px;
  height: 70px;
  margin-right: 10px;
  border: solid 1px rgba(0, 0, 0, 0.2);
  border-radius: 70%;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  display: flex;
  justify-content: center;
}

.pofileImg {
  width: 70px;
  /* height: 1px; */
  object-fit: cover;
}

.activityContent {
  width: 60%;
  height: 70px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
}
</style>
