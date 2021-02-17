<template>
  <div class="edit-profieImg-component">
    <div class="editImg-upload-title highlight">프로필 이미지 업로드</div>
    <clipper-upload class="select-Edit-Img"
      ><button class="select-Edit-Img-btn">
        이미지 선택
      </button></clipper-upload
    >
    <div class="flex">
      <clipper-fixed
        v-if="userImg"
        class="basic lg clipper-fixed"
        :src="`https://dtbqjjy7vxgz8.cloudfront.net/${userImg}`"
        :round="true"
        ref="clipper"
        preview="fixed-preview"
        :rotate="rotation"
      ></clipper-fixed>

      <clipper-fixed
        v-else
        class="basic lg clipper-fixed"
        :src="src[0]"
        :round="true"
        ref="clipper"
        preview="fixed-preview"
        :rotate="rotation"
      ></clipper-fixed>
    </div>
    <div class="edit-btn-container">
      <button @click="profilegoback">돌아가기</button>
      <button @click="getResult">저장</button>
    </div>
  </div>
</template>

<script>
import { clipperFixed } from "vuejs-clipper";
import { clipperUpload } from "vuejs-clipper";
export default {
  components: {
    clipperFixed,
    clipperUpload,
  },
  props: {
    userImg: String,
  },
  data() {
    return {
      src: [require("@/assets/img/profileM.svg")],
      rotation: 0,
      resultURL: null,
    };
  },
  methods: {
    getResult() {
      const canvas = this.$refs.clipper.clip(); //call component's clip method
      this.resultURL = canvas.toDataURL("image/jpeg", 1); //canvas->image

      const aaa = this.dataURLtoFile(
        this.resultURL,
        `${this.$store.state.userId}.png`
      );
      console.log(aaa);
      if (this.resultURL) {
        this.$emit("previewURL", this.resultURL);
        this.$emit("edit_url", aaa);
      }
      this.$emit("endUrlEdit", false);
    },
    profilegoback() {
      this.$emit("endUrlEdit", false);
    },
    dataURLtoFile(dataurl, filename) {
      var arr = dataurl.split(","),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }
      return new File([u8arr], filename, { type: mime });
    },
  },
};
</script>

<style scoped>
.flex {
  display: flex;
  flex-wrap: wrap;
  width: 80%;
}
.flex .lg {
  flex-basis: 0;
  flex-grow: 3;
}
.flex .md {
  flex-basis: 0;
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
