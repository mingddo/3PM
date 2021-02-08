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
      v-model="form.checkedprivate"
    />
    <button @click="createGroup">그룹만들기</button>
  </div>
</template>

<script>
import { mapState } from 'vuex'

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
      type: 'NEW',
      imageUrl: ``,
      previewUrl : [],
      fileList: [],
      completed: false,
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
    async createGroup () {
      const formData = new FormData ();
      formData.append('file', this.fileList)
      formData.append('grouptitle', this.form.grouptitle)
      formData.append('groupintrouduce', this.form.groupintrouduce)
      formData.append('private', this.form.checkedprivate)
      formData.append('user', this.userpk)
      this.completed = true;
      console.log(formData)
      if (this.type == 'NEW' || this.type == 'SHARE') {
        // axios create 요청

          // await createFeed (
          //   formData,
          //   () => {
          //     this.$router.push({ name: 'groupdetail' })
          //     // this.$router.push({ name: 'groupdetail', query: { id : res.data, Category: this.Category } })
          //   },
          //   (err) => {
          //     console.log(err)
          //     alert('인증된 유저만 작성 가능합니다.')
          //   }
          // )
      } else {
        // axios put 요청
        if (this.userpk == this.$route.params.feed.user.id) {
            // await updateFeed (
            //   this.$route.params.feed.id,
            //   formData,
            //   (res) => {
            //     this.$router.push({ name: 'groupdetail', query: { id : res.data, Category: this.Category } })
            //   },
            //   (err) => {
            //     console.log(err)
            //     alert('본인만 수정할 수 있습니다.')
            //   }
            // )
          }
        else {
          alert('본인만 수정할 수 있습니다.')
        }
      }
    },
  },
    beforeRouteLeave (to, from, next) {
    if (this.completed) {
      next();
    } else {
      const answer =
        window.confirm('작성 중인 내용이 저장되지 않았습니다. 화면을 나가시겠습니까?')
      if (answer) {
        next();
      } else {
        next(false);
      }
    }
  },
  computed: {
    ...mapState({
      userpk : (state) => state.userId,
    })
  },
};
</script>

<style></style>
