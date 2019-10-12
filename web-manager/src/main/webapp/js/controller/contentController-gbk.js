 //���Ʋ� 
app.controller('contentController' ,function($scope,$controller ,uploadService,contentCategoryService  ,contentService){	
	
	$controller('baseController',{$scope:$scope});//�̳�
	
    //��ȡ�б����ݰ󶨵�����  
	$scope.findAll=function(){
		contentService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//��ҳ
	$scope.findPage=function(page,rows){			
		contentService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//�����ܼ�¼��
			}			
		);
	}
	
	//��ѯʵ�� 
	$scope.findOne=function(id){				
		contentService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//���� 
	$scope.save=function(){				
		var serviceObject;//��������  				
		if($scope.entity.id!=null){//�����ID
			serviceObject=contentService.update( $scope.entity ); //�޸�  
		}else{
			serviceObject=contentService.add( $scope.entity  );//���� 
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
		contentService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//ˢ���б�
                    window.location.reload()
					$scope.selectIds = [];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//������������ 
	
	//����
	$scope.search=function(page,rows){			
		contentService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;
				$scope.paginationConf.totalItems=response.total;//�����ܼ�¼��
			}			
		);
	}
    
	// �ļ��ϴ��ķ���:
	$scope.uploadFile = function(){
		uploadService.uploadFile().success(function(response){
			if(response.success){
				$scope.entity.pic = response.message;
			}else{
				alert(response.message);
			}
		});
	}
	
	// ��ѯ���й�����
	$scope.findContentCategoryList = function(){
		contentCategoryService.findAll().success(function(response){
			$scope.contentCategoryList = response;
		});
	}
	
	$scope.status = ["��Ч","��Ч"];
});	
