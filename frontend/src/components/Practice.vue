<template>
  <div>
    <div v-if="editing">
      <h4>프로필 이미지 업로드</h4>
      <clipper-upload v-model="src[0]"
        ><button>이미지 업로드</button></clipper-upload
      >
      <div class="flex">
        <clipper-fixed
          class="basic lg clipper-fixed"
          :src="src[0]"
          :round="true"
          ref="clipper"
          preview="fixed-preview"
          :rotate="rotation"
        ></clipper-fixed>
      </div>
      <button @click="getResult">이미지 선택</button>
    </div>
    <div v-else>
      <div>
        프로필
      </div>
      <div class="profile-edit-img-frame">
        <img v-if="userInfo.user_img" :src="userInfo.user_img" alt="" />
        <img v-else src="@/assets/img/profileM.svg" alt="" width="80px" />
      </div>
      <button>프로필 이미지 수정</button>
      <div>닉네임 : {{ userInfo.nickname }} <button>수정</button></div>
      <hr />
      <div>프로필 소개</div>
      <div v-if="userInfo.introduce">{{ userInfo.introduce }}</div>
      <div v-else>
        프로필 소개가 아직 없습니다.
        <button @click="writeIntroduce = true">작성하기</button>
      </div>
      <div
        :class="[writeIntroduce ? 'write-introduce active' : 'write-introduce']"
      >
        <input
          type="text"
          placeholder="프로필 소개를 입력해 주세요"
          width="400px"
          height="50px"
        />
        <button>저장하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { clipperFixed } from "vuejs-clipper";
import { clipperUpload } from "vuejs-clipper";
import { userInfoDetail } from "@/api/mypage.js";
export default {
  components: {
    clipperFixed,
    clipperUpload,
  },
  data() {
    return {
      src: ["@/assets/img/00.jpg"],
      rotation: 0,
      resultURL: null,
      editing: false,
      userInfo: {},
      writeIntroduce: false,
    };
  },
  methods: {
    getResult() {
      const canvas = this.$refs.clipper.clip(); //call component's clip method
      this.resultURL = canvas.toDataURL("image/jpeg", 1); //canvas->image
      console.log("유알엘 변환", URL.revokeObjectURL(this.resultURL));
    },
    getUserProfileInfo() {
      userInfoDetail(
        this.$store.state.userId,
        (res) => {
          console.log("axios res", res.data);
          this.userInfo = res.data;
        },
        (err) => {
          console.error(err);
        }
      );
    },
  },
  created() {
    this.getUserProfileInfo();
  },
};
</script>

<style scoped>
.flex {
  display: flex;
  flex-wrap: wrap;
  width: 60%;
}
.flex .lg {
  flex-basis: 0;
  flex-grow: 3;
  margin: 3px;
}
.flex .md {
  flex-basis: 0;
  margin: 3px;
  flex-grow: 2;
}
.basic {
  max-width: 700px;
  min-width: 270px;
}
.empty-height {
  height: 500px;
}
</style>
