<template>
  <div>
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
</template>

<script>
import { clipperFixed } from "vuejs-clipper";
import { clipperUpload } from "vuejs-clipper";
export default {
  components: {
    clipperFixed,
    clipperUpload,
  },
  data() {
    return {
      src: [
        "https://timtnleeproject.github.io/vuejs-clipper/dawn.jpg",
        "https://timtnleeproject.github.io/vuejs-clipper/ex2.jpg",
      ],
      rotation: 0,
      resultURL: null,
    };
  },
  methods: {
    getResult() {
      const canvas = this.$refs.clipper.clip(); //call component's clip method
      this.resultURL = canvas.toDataURL("image/jpeg", 1); //canvas->image
      console.log(this.resultURL);
    },
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
