<template>
  <div class="subscribedComponentFrame" @click="goToProfile">
    <div style="display:flex">
      <div class="subscribeImgFrame">
        <img class="pofileImg" :src="`https://dtbqjjy7vxgz8.cloudfront.net/${subscriber.profileImg}`" alt="프로필사진" />
      </div>
      <div class="subscriberName">
        <div>{{ subscriber.nickname }}</div>
      </div>
    </div>
    <div>
      <button class="deleteSubscribe" @click="deleteSubscribe">구독삭제</button>
    </div>
  </div>
</template>

<script>
import { followToggle } from "@/api/mypage.js";
import Swal from 'sweetalert2';

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
      Swal.fire({ 
        title: '구독 취소하시겠습니까?', 
        text: '', 
        icon: 'warning', 
        showCancelButton: true, 
        confirmButtonColor: '#3085d6', 
        cancelButtonColor: '#d33', 
        confirmButtonText: '구독 취소하기', 
        cancelButtonText: '돌아가기'
      }).then(result => {
        if (result.isConfirmed) {
          followToggle(
            this.subscriber.id,
            (res) => {
              console.log(res)
              this.$emit('decrement')},
            (err) => {console.log(err);}
          );
        } else {
          return;
        }
      });


      // if (confirm("구독 취소하시겠습니까?")) {
      //   followToggle(
      //     this.subscriber.id,
      //     (res) => {
      //       console.log(res)
      //       this.$emit('decrement')},
      //     (err) => {console.log(err);}
      //   );
      // }
      // else return
    },
  }
}
</script>

<style scoped>
.subscribedComponentFrame {
  width: 100%;
  height: 70px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom : 10px;
  padding: 10px;
  border: none;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
  background-color: #fffcf9;
}
.subscribedComponentFrame:hover {
  background-color: rgba(0,0,0,0.15);
  border-radius: 10px;
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
  min-width: 50px;
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
  background-color: salmon;
  min-width: 80px;
  z-index: 100;
  margin-left: auto;
}
</style>
