<template>
  <div class="message">
      <h2>留言板</h2>
      <div class="input-msg">
          <textarea name="" id="msg" cols="50" rows="5" v-model.trim="content" @keyup.enter="sendMsg" placeholder="请输入评论内容"></textarea>
          <button class="send-msg" @click="sendMsg">发送留言</button>
      </div>
      <div class="history">
          <div class="msg-item" v-for="msg in msgList" :key="msg.id">
                <div>
                    <h2>{{msg.account}}<small>{{msg.c_date}}</small></h2>
                    <p class="cont">{{msg.c_comment}}</p>
                    <p class="control">
                        <button class="btn btn-del" @click="delMsg(msg.id)">删除</button>
                    </p>
                </div>
          </div>
      </div>
  </div>
</template>

<script>
export default {
    data(){
        return{
            // 双向绑定的留言内容
            content: "",
            // 留言内容
            msgList: [],
            //当前日期 
            c_date:'',
            time:''
        }   
    },
    mounted() {
        this.getMsgList()
    },
    methods: {
        getMsgList(){
            this.$http.get("/critic/findAll")
            .then(res=>{
                this.msgList = res.data.data;
            })
        },
        sendMsg () {
            // console.log("用户准备发表留言",this.content)
            if(this.content===""){
                alert("留言内容为空")
                return
            }
            this.time = this.getCurrentTime();
            this.$http.get("/critic/insert",{
                params:{
                    account: window.sessionStorage.getItem('userForm.account'),    
                    c_date:this.time,
                    c_comment: this.content,
                }
            })
            .then(res=>{
                this.content=''
                this.getMsgList()
                alert("留言成功")
            })
        },
        editMsg(msg) {
            // 给编辑的id赋值
            this.editId = msg.id
            // 给输入框绑定的变量赋值（展示编辑数据）
            this.content = msg.content
        },
        delMsg(id) {
            // 二次确认
            const result = confirm("确认删除？")
            if (!result) return
            this.$http.get("/critic/delete/"+id)
            .then(res=>{
                console.log(res);
                this.getMsgList()
            })
        },
        //获取当前时间
        getCurrentTime(){
            const currentDate = new Date();
            const year = currentDate.getFullYear();
            const month = (currentDate.getMonth()+1).toString().padStart(2,'0');
            const day = currentDate.getDate().toString().padStart(2,'0');
            //格式化时间
            const formattedTime = year+'-'+month+'-'+day;
            return formattedTime;
        }
    },
}
</script>
<!-- 如果想让这里编写的样式只对当前组件有效，style上添加一个scoped属性-->
<style scoped>
.input-msg{
    width: 80%;
    height: 200px;
    margin: 10px auto;
    display: flex;
    flex-direction: column;
    align-items: flex-end;
}
#msg{
    width: 100%;
    height: 180px;
    padding: 10px;
    outline: none;
    border: 1px solid rgba(219, 73, 73, 0.466);
    border-radius: 5px;
    resize: none;
    display: block;
    margin: 10px auto;
    font-size: 18px;
}
.send-msg{
    width: 200px;
    height: 40px;
    border: none;
    background-color: orangered;
    color: wheat;
    border-radius: 20px;
    cursor: pointer;
    font-size: 18px;
}
.send-msg:hover{
    background-color: rgb(34, 231, 109);
    color: #000;
}
.history{
    width: 80%;
    padding: 20px 0px;
    margin: 10px auto;
    background-color: #fff;
}
.msg-item{
    display: flex;
    padding: 10px;
    border-bottom: 1px dashed #888;
}
.msg-item img{
    width: 100px;
    height: 100px;
    border-radius: 10px;
}
.msg-item div{
    margin-left: 10px;
    width: 100%;
}
.msg-item div h2{
    font-size: 22px;
}
.msg-item div h2 small{
    font-size: 16px;
    color: #888;
    font-weight: 600;
    margin-left: 20px;
}
.msg-item div p.cont{
    font-size: 16px;
    color: #444;
    margin: 10px 0;
    word-wrap: break-word;
    word-wrap: break-word;
    white-space: pre-wrap;
    min-height: 50px;
    /* border: 1px solid red; */
}
.control{
    width: 100%;
    display: flex;
    justify-content: flex-end;
}
.btn{
    width: 80px;
    height: 30px;
    border: 1px solid #888;
    border-radius: 15px;
    cursor: pointer;
}
.btn-edit{
    background-color: rgb(20, 187, 247);
}
.btn-edit:hover{
    background-color: rgb(17, 148, 196);
    color: #fff;
}
.btn-del{
    background-color: rgb(247, 20, 88);
}
.btn-del:hover{
    background-color: rgb(192, 21, 72);
    color: #fff;
}
.message{
  width: 80%;
}
</style>
