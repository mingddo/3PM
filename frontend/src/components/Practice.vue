<template>
  <div class="edit-profieImg-component">
    <div class="editImg-upload-title highlight">프로필 이미지 업로드</div>
    <clipper-upload 
      class="select-Edit-Img"
      v-model="src"
    >
      <button class="select-Edit-Img-btn" @click="show">
        이미지 선택
      </button>
    </clipper-upload>
    <div class="flex">
      <clipper-fixed
        class="basic lg clipper-fixed"
        :src="src"
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
      src: '',
      rotation: 0,
      resultURL: null,
      fileExtension: '',
    };
  },
  methods: {
    show() {
      console.log('hi');
    },
    getResult() {
      const canvas = this.$refs.clipper.clip(); //call component's clip method

      let idx = 0;
      const length = this.userImg.length;
      // .위치 찾기
      for (let i = 0; i < length; i++) {
        if (this.userImg.charAt(i) === '.') {
          idx = i;
          break;
        }
      }
      this.fileExtension = this.userImg.slice(idx + 1);      

      this.resultURL = canvas.toDataURL(`image/${this.fileExtension}`, 1); //canvas->image

      const aaa = this.dataURLtoFile(
        this.resultURL,
        `${this.$store.state.userId}.${this.fileExtension}`
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
        // mime = `image/${this.fileExtension}`,
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }
      return new File([u8arr], filename, { type: mime });
    },
    save(){
      if(this.userImg){
        this.src = `https://dtbqjjy7vxgz8.cloudfront.net/${this.userImg}`
      }
    }
  },
  mounted(){
    this.save()
  }
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
