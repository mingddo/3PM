<template>
  <div>
    <div class="newsfeed-body">
      <Sidebar />
      <div class="group-detail">
        <GroupNav :isHome="false"/>
        <div class="newsfeed-form-img-box">
          <label for="image"> <i class="far fa-images"></i> 그룹 이미지 등록하기 </label>
          <input
            class="newsfeed-form-img-input"
            id="image"
            type="file"
            @change="selectFile"
            accept="image/*"
          />
        </div>
        <div class="group-detail-img-space" v-if="imageUrl">
          <img class="group-detail-img" :src="imageUrl" alt="">
        </div>
        <div class="group-form">
          <input
            type="text"
            name="group_title"
            placeholder="그룹 이름을 입력해주세요"
            v-model="form.name"
            class="group-form-name"
          />
          <textarea
            name="groupintroduce"
            placeholder="그룹 설명을 입력해주세요"
            cols="30"
            rows="10"
            v-model="form.description"
            class="group-form-descript"
          ></textarea>
        </div>
        <div class="group-form-submit-btn">
          <button class="create-group-btn" @click="create">그룹만들기</button>
        </div>
        <div class="fa-3x group-form-loading" v-if="uploadingImg">
          <i class="fas fa-spinner fa-spin"></i>
          <div>
            이미지 업로드 중입니다
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { createGroup } from '@/api/group.js'
import { createGroupImg } from '@/api/group.js'
import Sidebar from '../../components/Common/Sidebar.vue';
import GroupNav from '../../components/GroupFeed/GroupNav.vue';

export default {
  components: { Sidebar, GroupNav },
  data(){
    return{
      uploadingImg: false,
      form: {
        name: "",
        description: "",
      },
      completed: false,
      fileSelect: false,
      selectedFile: null,
      imageUrl: null,
    }
  },
  methods:{
    selectFile (e) {
      let files = e.target.files || e.dataTransfer.files;
      if (files.length) {
        this.fileSelect = true;
        this.selectedFile = files[0]
        let reader = new FileReader();
        reader.onload = (e) => {
          this.imageUrl = e.target.result;
          console.log(this.imageUrl)
          // this.previewUrl.push(this.imageUrl)
        }
        reader.readAsDataURL(this.selectedFile);
      }
    },
    imgUpload (id) {
      const formData = new FormData ();
      formData.append('file', this.selectedFile)
      createGroupImg(
        id,
        formData,
        (res) => {
          console.log(res)
        },
        (err) => {
          console.log(err)
        }
      )
    },
    create () {
      this.completed = true;
      if (!this.form.name) {
        alert('그룹 이름을 입력해주세요')
      } else if (!this.form.description) {
        alert('그룹 소개를 입력해주세요')
      } else {
        createGroup(
          this.form,
          (res) => {
            if (this.selectedFile) {
              this.imgUpload(res.data.id)
              this.uploadingImg = true;
              setTimeout(() => {
                this.$router.push({ name: 'groupdetail', query: { groupId : res.data.id } })
              }, 1000);
            } else {
              this.$router.push({ name: 'groupdetail', query: { groupId: res.data.id }})
            }
            console.log(res)
          },
          (err) => {
            console.log(err)
          }
        )
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

<style>
.group-form {
  margin-top: 50px;
}
.group-form-name {
  width: 90%;
  padding: 10px;
  border: 0;
  background-color: transparent;
  border-bottom: 2px solid #9e7f6d;
  margin-top: 30px;
  margin-bottom: 30px;
}
.group-form-descript {
  width: 100%;
  padding: 10px;
  border: none;
  background-color: #fffcf9;
  border-radius: 10px;
  margin-top: 30px;
  margin-bottom: 30px;
}
.group-form-submit-btn {
  display: flex;
  justify-content: center;
}
.create-group-btn {
  height: 40px;
  background-color: #f0d3c1;
  width: 150px;
  border-radius: 5px;
  margin-top: 30px;
  margin-bottom: 30px;
}
.group-form-loading {
  background-color: rgba(0, 0, 0, 0.3);
  width: 100vw;
  height: 100vh;
  z-index: 100;
  text-align: center;
  position: fixed;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  -moz-transform: translate(-50%, -50%);
  -o-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.group-form-loading > i {
  font-size: 50px;
  margin: 10px;
}
</style>
