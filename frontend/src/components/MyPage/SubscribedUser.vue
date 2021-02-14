<template>
  <div class="subscribedComponentFrame" @click="goToProfile">
    <div class="subscribeImgFrame">
      <img class="pofileImg" :src="`https://dtbqjjy7vxgz8.cloudfront.net/${subscriber.profileImg}`" alt="프로필사진" />
    </div>
    <div class="subscriberName">
      <div>{{ subscriber.nickname }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    subscriber: Object,
  },
  methods : {
    goToProfile () {
      this.$router.push({ name: 'MyPage', query: { name: this.subscriber.id}})
    },
  }
};
</script>

<style scoped>
.subscribedComponentFrame {
  width: 45%;
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
