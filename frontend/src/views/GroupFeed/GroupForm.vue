<template>
  <div>
    새 그룹 만들기
    <div>
      <div>
        <input
          type="text"
          name="group_title"
          placeholder="그룹 이름을 입력해주세요"
          v-model="form.grouptitle"
        />
        <textarea
          name="groupintroduce"
          placeholder="그룹 설명을 입력해주세요"
          cols="30"
          rows="10"
          v-model="form.groupintrouduce"
        ></textarea>
      </div>
      <div>
        <label for="image"> <i class="far fa-images"></i> 사진 </label>
        <input
          class="newsfeed-form-img-input"
          id="image"
          type="file"
          @change="selectFile"
          accept="image/*"
        />
      </div>
      <div>
        <label for="video"> <i class="fas fa-video"></i> 동영상 </label>
        <input
          class="newsfeed-form-img-input"
          id="video"
          type="file"
          @change="selectFile"
          accept="video/*"
        />
      </div>
    </div>
    <div class="newsfeed-form-img">
      <div v-for="(view, idx) in previewUrl" :key="idx">
        <div class="newsfeed-form-img-prevbox">
          <!-- <i class="fas fa-times-circle newsfeed-form-img-prevbox-delbtn"></i> -->
          <img
            class="newsfeed-form-img-prevbox-delbtn"
            src="https://img.icons8.com/fluent/20/000000/close-window.png"
          />
          <!-- <i class="far fa-times-circle  newsfeed-form-img-prevbox-delbtn"></i> -->
          <img :src="view" alt="미리보기 이미지" width="100%" />
        </div>
      </div>
      <div class="newsfeed-form-img-prevbox">
        사진
      </div>
    </div>
    <label for="private">비공개</label>
    <input
      type="checkbox"
      name="private"
      id="private"
      v-model="checkedprivate"
    />
  </div>
</template>

<script>
export default {
  data(){
    return{
      form: {
        tags: [],
        file: null,
        grouptitle: null,
        groupintrouduce:null,
        checkedprivate: false,
      },
      imageUrl: ``,
      previewUrl : [],
      fileList: [],
    }
  },
  methods:{
  selectFile (e) {
      let files = e.target.files || e.dataTransfer.files;
      this.fileList.push(files.[0])
      console.log(this.fileList)
      if (files.length) {
        this.form.file = files[0]
        console.log(this.form.file)
        // this.imageUrl = URL.createObjectURL(this.form.file);
        // console.log(this.imageUrl)
        let reader = new FileReader();
        reader.onload = (e) => {
          // console.log(e.target.result)
          this.imageUrl = e.target.result;
          this.previewUrl.push(this.imageUrl)
        }
        reader.readAsDataURL(this.form.file);
      }
    },
  },
};
</script>

<style></style>
