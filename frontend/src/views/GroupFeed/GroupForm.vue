<template>
  <div>
    <div class="newsfeed-body">
      <Sidebar />
      <div class="group-detail">
        <GroupNav :isHome="false"/>
        <div class="group-form-img-space">
          <label v-if="!isSelect" class="group-form-img-space-label" for="image"> <i class="far fa-images"></i> 그룹 이미지 </label>
          <label v-else class="group-detail-img-space" for="image"><img class="group-form-img-space-label" style="cursor: pointer;" :src="imageUrl" alt=""></label>
          <input
            id="image"
            type="file"
            @change="selectFile"
            accept="image/*"
          />
          <div class="group-form-img-guide"> <span class="highlight">그룹 이미지</span> <div v-if="isSelect" style="font-size: 12px;"> 변경을 원하시면 이미지를 다시 클릭해주세요 </div> </div>
          
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
      isSelect: false,
    }
  },
  methods:{
    selectFile (e) {
      this.isSelect = true;
      let files = e.target.files || e.dataTransfer.files;
      if (files.length) {
        this.fileSelect = true;
        this.selectedFile = files[0]
        let reader = new FileReader();
        reader.onload = (e) => {
          this.imageUrl = e.target.result;
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
        () => {
        },
        (err) => {
          console.log(err)
        }
      )
    },
    create () {
      this.completed = true;
      if (!this.form.name) {
        // alert('그룹 이름을 입력해주세요')
        this.$swal.fire({
          icon: 'error',
          text: '그룹 이름을 입력해주세요',
          showConfirmButton: false,
          timer: 1500
        })
      } else if (!this.form.description) {
        // alert('그룹 소개를 입력해주세요')
        this.$swal.fire({
          icon: 'error',
          text: '그룹 소개를 입력해주세요',
          showConfirmButton: false,
          timer: 1500
        })
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
      this.$swal.fire({ 
        text: '작성 중인 내용이 저장되지 않았습니다. 화면을 나가시겠습니까?', 
        icon: 'warning', 
        showCancelButton: true, 
        confirmButtonText: '나가기', 
        cancelButtonText: '돌아가기'
      }).then(result => {
        if (result.isConfirmed) {
          next();
        } else {
          next(false);
        }
      });

      // const answer =
      //   window.confirm('작성 중인 내용이 저장되지 않았습니다. 화면을 나가시겠습니까?')
      // if (answer) {
      //   next();
      // } else {
      //   next(false);
      // }
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
  background-color: #fff9f3;
  border-bottom: 2px solid #9e7f6d;
  margin-top: 30px;
  margin-bottom: 30px;
}
.group-form-descript {
  width: 100%;
  padding: 10px;
  border: none;
  background-color: #fff9f3;
  border-radius: 10px;
  margin-top: 30px;
  margin-bottom: 30px;
}
.group-form-submit-btn {
  display: flex;
  justify-content: center;
}
.group-form-img-space-label {
  margin: auto;
  cursor: pointer;
  width: 150px;
  height: 150px;
  text-align: left;
  border: solid 1px rgba(0, 0, 0, 0.2);
  border-radius: 100%;
  box-shadow: 0 1px 3px rgb(0 0 0 / 20%);
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fff9f3;

  /* margin: 24px;
  position: relative;
  height: 100px;
  width: 100px;
  border: 2px dashed rgb(158, 127, 109);
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center; */
  /* padding: 0.5em 0.75em; */
  /* font-size: inherit;
  line-height: normal;
  vertical-align: middle;
  cursor: pointer; */
}
.group-form-img-space-label-selected {

}
.group-form-img-guide {
  margin-top:10px;
  text-align: center;
}
.group-form-img-space input[type="file"] {
  display: none;
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
