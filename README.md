# 💰 Expense Tracker (Harcama Takip Sistemi)

Bu proje, **Java** kullanılarak geliştirilmiş, Nesne Yönelimli Programlama (OOP) prensiplerini temel alan terminal tabanlı bir harcama takip uygulamasıdır. Kullanıcıların aylık bütçelerini belirlemelerine, harcamalarını kategorize etmelerine ve kalan bakiyelerini anlık olarak görmelerine olanak tanır.

## 🚀 Özellikler

* **Bütçe Yönetimi:** Kullanıcıdan aylık bütçe bilgisini alır ve yönetir.
* **Harcama Kategorileri:** Yiyecek (Food), Ulaşım (Transport) ve Fatura (Bill) gibi farklı kategorilerde harcama girişi.
* **Harcama Özeti:** Girilen tüm harcamaların tarih, tutar ve açıklama detaylarıyla listelenmesi.
* **Bakiye Takibi:** Toplam harcanan tutarı ve kalan bütçeyi otomatik hesaplama.
* **Hata Kontrolü:** Negatif sayı girişi veya hatalı tarih formatlarına karşı kullanıcı dostu hata yönetimi.

## 🛠 Kullanılan Teknolojiler ve OOP Kavramları

Bu projede aşağıdaki OOP prensipleri aktif olarak kullanılmıştır:

* **Kalıtım (Inheritance):** `FoodExpense`, `TransportExpense` ve `BillExpense` sınıfları, temel `Expense` sınıfından türetilmiştir. Bu sayede kod tekrarı önlenmiştir.
* **Soyutlama (Abstraction):** `Expense` sınıfı soyut (abstract) olarak tanımlanmış ve `getCategory()` metodu alt sınıflar tarafından özelleştirilmiştir.
* **Kapsülleme (Encapsulation):** Kullanıcı ve harcama verileri (description, amount vb.) sınıf içinde korunmuş, erişimler metotlar üzerinden sağlanmıştır.
* **Çok Biçimlilik (Polymorphism):** Harcamalar `List<Expense>` içerisinde tutulur, ancak çalışma zamanında her nesne kendi sınıfına özgü davranışı sergiler.

## 📂 Sınıf Yapısı (Class Structure)

* **ExpenseTracker:** `main` metodunu içerir, kullanıcı etkileşimini yönetir.
* **User:** Kullanıcı bilgilerini ve harcama listesini tutar.
* **Expense (Abstract):** Tüm harcama türleri için temel sınıf.
    * `FoodExpense`
    * `TransportExpense`
    * `BillExpense`

## screen Ekran Görüntüleri

_(Buraya raporundaki ekran görüntülerinden birini ekleyebilirsin, örneğin sequence screenshot)_

## 💻 Kurulum ve Çalıştırma

1. Projeyi bilgisayarınıza indirin:
   ```bash
   git clone [https://github.com/KULLANICI_ADIN/ExpenseTracker.git](https://github.com/KULLANICI_ADIN/ExpenseTracker.git)
