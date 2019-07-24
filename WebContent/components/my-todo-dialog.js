(function(globe){
if (!globe.Vue) {console.warn("可能你还没导入Vue的引用。。。");}
if(arguments.length<2) {console.warn('参数不对');return;}
for(let i=1;i<arguments.length;i++){
Vue.component(arguments[i].name, arguments[i]);
}
})(window, 

(()=>{let module = {};
module.exports = {
    name: 'my-todo-dialog',
    props: ['title'],
    data() {
        return {
            showFlag: false,
            username: null,
            items: []
        }
    },
    mounted() {
        this.fetchData();
    },
    updated() {},
    methods: {
        show() {
            this.showFlag = true;
        },
        hide() {
            this.showFlag = false;
        },
        fetchData: function() {
            let vm = this;
            $.ajax({
                url: webRoot + "/userAlert.do",
                success: function(data) {
                    vm.items = data.items;
                },
                error: function(e) {
                    console.error(e);
                }
            });
        }
    }
}
module.exports.template = "<jxiaui-dialog @close=\"hide\" v-if=\"showFlag\" :title=\"title\">\r\n    <div class=\"my-todo-dialog-content\">\r\n        <div>\r\n            <div v-for=\"item in items\" v-if=\"item.count>0\">\r\n                {{item.label}}:<a :href=\"item.url\">{{item.count}}</a>\r\n            </div>\r\n        </div>\r\n    </div>\r\n</jxiaui-dialog>"
return module.exports;})(), 

)
