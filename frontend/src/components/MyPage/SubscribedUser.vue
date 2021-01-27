<template>
  <div class="subscribedComponentFrame">
    <div class="subscribeImgFrame">
      <img class="pofileImg" src="@/assets/loverduck.png" alt="프로필사진" />
    </div>
    <div class="subscriberName">
      <div>{{ subscriber.username }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    subscriber: Object,
  },
};
</script>

<style scoped>
.subscribedComponentFrame {
  width: 90%;
  height: 85px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin: 5px;
  padding: 10px;
  border: none;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}

.subscribeImgFrame {
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
  width: 90px;
  /* height: 1px; */
  object-fit: cover;
}

.subscriberName {
  width: 60%;
  height: 70px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}

.subscriberName div {
  font-weight: 700;
}
</style>
