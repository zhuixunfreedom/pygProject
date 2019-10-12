//���Ʋ�
app.controller('goodsController' ,function($scope,$controller,itemCatService,goodsService){

    $controller('baseController',{$scope:$scope});//�̳�

    //��ȡ�б����ݰ󶨵�����
    $scope.findAll=function(){
        goodsService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    }

    //��ҳ
    $scope.findPage=function(page,rows){
        goodsService.findPage(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//�����ܼ�¼��
            }
        );
    }

    //��ѯʵ��
    $scope.findOne=function(id){
        goodsService.findOne(id).success(
            function(response){
                $scope.entity= response;
            }
        );
    }

    //����
    $scope.save=function(){
        var serviceObject;//��������
        if($scope.entity.id!=null){//�����ID
            serviceObject=goodsService.update( $scope.entity ); //�޸�
        }else{
            serviceObject=goodsService.add( $scope.entity  );//����
        }
        serviceObject.success(
            function(response){
                if(response.success){
                    //���²�ѯ
                    $scope.reloadList();//���¼���
                }else{
                    alert(response.message);
                }
            }
        );
    }


    //����ɾ��
    $scope.dele=function(){
        //��ȡѡ�еĸ�ѡ��
        goodsService.dele( $scope.selectIds ).success(
            function(response){
                if(response.success){
                    $scope.reloadList();//ˢ���б�
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity={};//������������

    //����
    $scope.search=function(page,rows){
        goodsService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//�����ܼ�¼��
            }
        );
    }

    // ��ʾ״̬
    $scope.status = ["δ���","���ͨ��","���δͨ��","�ر�"];

    $scope.itemCatList = [];
    // ��ʾ����:
    $scope.findItemCatList = function(){
        itemCatService.findAll().success(function(response){
            for(var i=0;i<response.length;i++){
                $scope.itemCatList[response[i].id] = response[i].name;
            }
        });
    }

// ��˵ķ���:
    $scope.updateStatus = function(status){
        goodsService.updateStatus($scope.selectIds,status).success(function(response){
            if(response.success){
                $scope.reloadList();//ˢ���б�
                $scope.selectIds = [];
            }else{
                alert(response.message);
            }
        });
    }
});