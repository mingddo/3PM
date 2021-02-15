<template>
  <div class="subscribedComponentFrame" @click="goToProfile">
    <div class="subscribeImgFrame">
      <img class="pofileImg" :src="`https://dtbqjjy7vxgz8.cloudfront.net/${subscriber.profileImg}`" alt="프로필사진" />
    </div>
    <div class="subscriberName">
      <div>{{ subscriber.nickname }}</div>
    </div>
    <div>
      <button class="deleteSubscribe" @click="deleteSubscribe">구독삭제</button>
    </div>
  </div>
</template>

<script>
import { followToggle } from "@/api/mypage.js";
export default {
  props: {
    subscriber: Object,
  },
  methods : {
    goToProfile (e) {
      if (e.target.classList.value === "deleteSubscribe") return
      this.$router.push({ name: 'MyPage', query: { name: this.subscriber.id}})
    },
    deleteSubscribe() { 
      followToggle(
        this.subscriber.id,
        (res) => {
          console.log(res)
          this.$emit('decrement')},
        (err) => {console.log(err);}
      );
    },
  }
}
</script>

<style scoped>
.subscribedComponentFrame {
  width: 100%;
  height: 70px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin: 7px;
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
.deleteSubscribe {
  z-index: 10000;
}
</style>
