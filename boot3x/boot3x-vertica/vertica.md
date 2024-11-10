
### 1) 다운로드

- https://www.vertica.com/ 로그인 후 `Support & Services` > `Downloads` 에서 다운로드 합니다.
- 다운로드 시 email 인증이 필요합니다.
- rocky 8 에 설치할 rpm 파일 다운로드 (vertica-23.4.0-0.x86_64.RHEL6.rpm)


### 2) 설치 준비

- 의존성: yum install gdb mcelog sysstat openssh which dialog chrony
- 커널 파라미터: /etc/sysctl.conf (vm.swappiness = 1)
- ntp 실행: systemctl enable chronyd && systemctl start chronyd
- THP

```
cat /sys/kernel/mm/transparent_hugepage/enabled
[always] madvise never <-- X

immediate

echo never | sudo tee /sys/kernel/mm/transparent_hugepage/enabled
echo never | sudo tee /sys/kernel/mm/transparent_hugepage/defrag

persist

vi /etc/default/grub
GRUB_CMDLINE_LINUX="... transparent_hugepage=never"


[root@rocky8-vertica ~]# grub2-mkconfig -o /boot/grub2/grub.cfg
Generating grub configuration file ...
Adding boot menu entry for EFI firmware configuration
done
[root@rocky8-vertica ~]# shutdown -r now # reboot 후 적용됨
```

- vertica 시스템 계정

```
/usr/sbin/groupadd -r verticadba
/usr/sbin/useradd -r -m -s /bin/bash -g verticadba dbadmin
passwd dbadmin
echo "export TZ="Asia/Seoul"" >> ~dbadmin/.bash_profile
echo "export LANG=en_US.utf8" >> ~dbadmin/.bash_profile
mkdir /data
mkdir /catalog
chown dbadmin:verticadba /data
chown dbadmin:verticadba /catalog
```

### 3) 설치

- cluster 구성으로 설치 (3개의 host 가 모두 booting 상태에서 설치 진행)

```
/opt/vertica/sbin/install_vertica \
  -s rocky8-vertica-node1,rocky8-vertica-node2,rocky8-vertica-node3 \
  -r /tmp/vertica-23.4.0-0.x86_64.RHEL6.rpm \
  -u dbadmin \
  -p 1 \
  --point-to-point
```

- database 생성

```
admintools \ 
  -t create_db \ 
  --database=cluster_db \ 
  --data_path=/data  \
  --catalog_path=/catalog \ 
  --password=1 \
  --hosts=rocky8-vertica-node1,rocky8-vertica-node2,rocky8-vertica-node3
```

- cluster 생태 확인

```
[dbadmin@rocky8-vertica-node1 ~]$ /opt/vertica/bin/admintools -t view_cluster
 DB         | Host | State 
------------+------+-------
 cluster_db | ALL  | UP    

[dbadmin@rocky8-vertica-node1 ~]$ ```