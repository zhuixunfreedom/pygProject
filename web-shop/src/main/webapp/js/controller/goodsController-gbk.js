//���Ʋ�
app.controller('goodsController' ,function($scope,$controller,$location,typeTemplateService ,itemCatService,uploadService ,goodsService){

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
    $scope.findOne=function(){
        var id = $location.search()['id'];
        // alert(id);
        goodsService.findOne(id).success(
            function(response){
                $scope.entity= response;

                // ���ô����ı��༭����
                editor.html($scope.entity.goodsDesc.introduction);

                // ����ͼƬ�б���ΪͼƬ��Ϣ�������JSON���ַ�������ǰ̨ʶ��ΪJSON��ʽ����
                $scope.entity.goodsDesc.itemImages = JSON.parse( $scope.entity.goodsDesc.itemImages );

                // ������չ����:
                $scope.entity.goodsDesc.customAttributeItems = JSON.parse( $scope.entity.goodsDesc.customAttributeItems );

                // ������
                $scope.entity.goodsDesc.specificationItems = JSON.parse($scope.entity.goodsDesc.specificationItems);

                // ����SKU�ļ���:
                for(var i=0;i<$scope.entity.itemList.length;i++){
                    $scope.entity.itemList[i].spec = JSON.parse( $scope.entity.itemList[i].spec );
                }
            }
        );
    }

    $scope.checkAttributeValue = function(specName,optionName){
        var items = $scope.entity.goodsDesc.specificationItems;
        var object = $scope.searchObjectByKey(items,"attributeName",specName);
        if(object != null){
            if(object.attributeValue.indexOf(optionName)>=0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    //����
    $scope.save=function(){
        // �����֮ǰ����ø��ı��༭���е����ݡ�
        $scope.entity.goodsDesc.introduction=editor.html();
        var serviceObject;//��������
        if($scope.entity.goods.id!=null){//�����ID
            serviceObject=goodsService.update( $scope.entity ); //�޸�
        }else{
            serviceObject=goodsService.add( $scope.entity  );//����
        }
        serviceObject.success(
            function(response){
                if(response.success){
                    //���²�ѯ
                    alert(response.message);
                    location.href="goods.html";
                }else{
                    alert(response.message);
                }
            }
        );
    }


    //����ɾ��
    $scope.dele=function(){
        var obj = document.getElementsByName("checkbox");
        var ids = [];
        for(var i in obj){
            if(obj[i].checked){
                ids.push(obj[i].value);
            }
        }
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

    // $scope.entity={goods:{},goodsDesc:{},itemList:[]}

    $scope.uploadFile = function(){
        // ����uploadService�ķ�������ļ����ϴ�
        uploadService.uploadFile().success(function(response){
            if(response.success){
                // ���url
                $scope.image_entity.url =  response.message;
            }else{
                alert(response.message);
            }
        });
    }

    // �����image_entity��ʵ�������{"color":"��ɫ","url":"http://192.168.209.132/group1/M00/00/00/wKjRhFn1bH2AZAatAACXQA462ec665.jpg"}
    $scope.entity={goods:{},goodsDesc:{itemImages:[],specificationItems:[]}};

    $scope.add_image_entity = function(){
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    }

    $scope.remove_iamge_entity = function(index){
        $scope.entity.goodsDesc.itemImages.splice(index,1);
    }

    // ��ѯһ�������б�:
    $scope.selectItemCat1List = function(){
        itemCatService.findByParentId(0).success(function(response){
            $scope.itemCat1List = response;
        });
    }

    // ��ѯ���������б�:
    $scope.$watch("entity.goods.category1Id",function(newValue,oldValue){
        itemCatService.findByParentId(newValue).success(function(response){
            $scope.itemCat2List = response;
        });
    });

    // ��ѯ���������б�:
    $scope.$watch("entity.goods.category2Id",function(newValue,oldValue){
        itemCatService.findByParentId(newValue).success(function(response){
            $scope.itemCat3List = response;
        });
    });

    // ��ѯģ��ID
    $scope.$watch("entity.goods.category3Id",function(newValue,oldValue){
        itemCatService.findOne(newValue).success(function(response){
            $scope.entity.goods.typeTemplateId = response.typeId;
        });
    });

    // ��ѯģ���µ�Ʒ���б�:
    $scope.$watch("entity.goods.typeTemplateId",function(newValue,oldValue){
        // ����ģ��ID��ѯģ�������
        typeTemplateService.findOne(newValue).success(function(response){
            $scope.typeTemplate = response;
            // ��Ʒ�Ƶ��ַ�������ת��JSON
            $scope.typeTemplate.brandIds = JSON.parse( $scope.typeTemplate.brandIds );

            // ����չ���Ե��ַ���ת��JSON
            if($location.search()['id'] == null){
                $scope.entity.goodsDesc.customAttributeItems = JSON.parse( $scope.typeTemplate.customAttributeItems );
            }

        });

        // ����ģ��ID��ù����б�����ݣ�
        typeTemplateService.findBySpecList(newValue).success(function(response){
            $scope.specList = response;
        });
    });

    $scope.updateSpecAttribute = function($event,name,value){
        // ���÷�װ�ķ����ж� ��ѡ�������Ƿ����:
        var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,"attributeName",name);

        if(object != null){
            // �ҵ���
            if($event.target.checked){
                object.attributeValue.push(value);
            }else{
                object.attributeValue.splice(object.attributeValue.indexOf(value),1);
            }

            if(object.attributeValue.length == 0){
                var idx = $scope.entity.goodsDesc.specificationItems.indexOf(object);
                $scope.entity.goodsDesc.specificationItems.splice(idx,1);
            }
        }else{
            // û�ҵ�
            $scope.entity.goodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]});
        }
    }

    // ����SKU����Ϣ:
    $scope.createItemList = function(){
        // ��ʼ����������:
        $scope.entity.itemList = [{spec:{},price:0,num:9999,status:'0',isDefault:'0'}];

        var items = $scope.entity.goodsDesc.specificationItems;

        for(var i=0;i<items.length;i++){
            //
            $scope.entity.itemList = addColumn($scope.entity.itemList,items[i].attributeName,items[i].attributeValue);
        }
    }

    addColumn = function(list,columnName,columnValues){
        // ����һ���������ڱ������ɵ�ÿ�е�����:
        var newList = [];
        // �����ü��ϵ�����:
        for(var i=0;i<list.length;i++){
            var oldRow = list[i];
            for(var j=0;j<columnValues.length;j++){
                // ��oldRow���ݽ��п�¡:
                var newRow = JSON.parse( JSON.stringify(oldRow) );
                newRow.spec[columnName]=columnValues[j];
                // ��newRow���뵽newList��
                newList.push(newRow);
            }

        }

        return newList;
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
});
