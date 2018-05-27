


# ボリューム変更


GUIでボリューム変更    


ボリュームが増えてることを確認    


```
lsblk
```

反映する    

```
growpart /dev/xvda 1
resize2fs /dev/xvda1
```

以上



# 参考

http://beyondjapan.com/blog/2017/02/ebs_update

