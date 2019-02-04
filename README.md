# 巡回セールスマン問題：全探索解法

Java 8u192

## 何
引数で指定した数の都市を10000 * 10000 平面状にランダムに配置，  
全ての都市を一回づつ周り，最初の都市に戻ってくるルートを総当たり的に探索し，最短となる経路を探します．爆発的に増大するので注意  
具体的には14都市超えたあたりから実行時間がとんでもないことになってくるはず  
13都市なら多分3分くらい  

400万パスに一回"#"を出力します

### 改良型(TSP2)
TSPのほうは毎回Point2Dクラスのdistanceメソッドを使って距離を計算していました  
しかしこれ何回も同じ組み合わせでdistance使ってるやん？ということで事前に全ての距離を計算，二次元配列cityDistancesに格納しておき，
ルート長計算の際はそこから引き出す方式にしました，それがTSP2  
こういう変更は2とかじゃなくてブランチでやるべきだと反省しています  

肝心の実行時間ですが，8%ほど削減に成功  
`java TSP 13  155.89s user 1.10s system 98% cpu 2:39.00 total`  
`java TSP2 13  142.32s user 0.71s system 99% cpu 2:23.84 total`

## HOW TO USE

ex. 12の都市を回るぞ 

```
$ time java TSP 12
java.awt.Point[x=1,y=9902]
java.awt.Point[x=15,y=2731]
java.awt.Point[x=7096,y=4558]
java.awt.Point[x=8204,y=1696]
java.awt.Point[x=6714,y=8233]
java.awt.Point[x=6003,y=9123]
java.awt.Point[x=4028,y=1922]
java.awt.Point[x=160,y=4206]
java.awt.Point[x=9243,y=838]
java.awt.Point[x=297,y=4179]
java.awt.Point[x=225,y=9660]
java.awt.Point[x=5225,y=6080]
####
minimal : 33451.121176488996
java TSP 12  12.64s user 0.22s system 98% cpu 13.107 total
```
