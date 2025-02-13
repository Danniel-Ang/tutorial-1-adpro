# Reflection 1: Refleksi Implementasi Fitur Baru pada Spring Boot

### Clean Code
1. **Meaningful Naming**
    - Nama variabel dan metode sudah deskriptif, seperti `createProductPage`, `findById`, `productListPage`, dll.

2. **Function**
    - `@Service`, `@Repository`, dan `@Controller` digunakan dengan benar untuk membedakan lapisan aplikasi.
    - Masing-masing fungsi dibuat sekecil mungkin dan bertujuan pada satu hal tertentu saja. Misalnya:
   ```java
   // Simpel, penamaan sudah deskriptif dan jelas.
   public Product findById(String productId) {
        for (Product product : productData) {
            if(product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
   ```

3. **Comments**
    - Tiap bagian kode sudah dijelaskan kegunaan-nya dengan nama function dan variable.

## Secure Coding Practices yang Diterapkan

1. **UUID untuk Menghindari Prediksi ID**
    - `UUID.randomUUID().toString();` menghindari prediksi ID, yang lebih aman dibandingkan auto-increment ID.

2. **Thymeleaf untuk Mencegah XSS**
    - Penggunaan `th:text="${product.productName}"` memastikan data ditampilkan sebagai teks murni, mencegah serangan XSS.

3. **Konfirmasi Penghapusan Produk**
    - `onclick="return confirm('Are you sure you want to delete this product?');"` mencegah penghapusan yang tidak disengaja.

## Perbaikan yang Dapat Dilakukan

1. **Validasi Input pada ProductController**
    - Masalah: Saat ini, tidak ada validasi untuk `productName` dan `productQuantity`.
    - Solusi: Tambahkan anotasi `@Valid` dan `@NotNull` pada model, serta validasi di controller.

2. **Gunakan Optional di Repository**
    - Masalah: `findById` mengembalikan `null` jika produk tidak ditemukan. Ini bisa menyebabkan `NullPointerException`.
    - Solusi: Gunakan `Optional<Product>` untuk hasil yang lebih aman.


# Reflection 2

1. **Perasaan setelah menulis unit test, berapa banyak unit test yang harus dibuat pada suatu class?**  
   Setelah menulis unit test, saya merasa lebih tenang karena dapat memastikan bahwa fitur yang diuji berjalan dengan baik tanpa harus melakukan pengecekan secara manual (black-box testing). Hal ini sangat membantu, terutama dalam program yang kompleks, karena sulit untuk memastikan seluruh fitur tetap berjalan dengan benar setiap kali ada perubahan kode jika hanya mengandalkan pengujian manual.
   Jumlah unit test yang dibuat dalam suatu kelas seharusnya bergantung pada kompleksitas kelas tersebut. Semakin kompleks sebuah kelas, semakin banyak unit test yang diperlukan untuk memastikan bahwa setiap fungsi, kondisi, pernyataan, dan skenario eksekusi telah diuji secara menyeluruh.

2. **Apakah 100 persen code coverage berati bebas dari bug dan error?**
   Tidak. Meskipun 100% code coverage berarti setiap fungsi, pernyataan, cabang, dan kondisi telah diuji setidaknya sekali, hal itu tidak menjamin bahwa kode bebas dari bug atau kesalahan logika. Jika unit test tidak memverifikasi hasil dengan benar atau tidak mencakup semua kemungkinan skenario kesalahan, maka bug masih bisa terjadi. Oleh karena itu, selain memastikan cakupan pengujian yang tinggi, penting juga untuk merancang test case yang efektif dan mencerminkan penggunaan nyata dari aplikasi.

3. **Evaluasi Clean Code dalam Functional Test Suite**
   Menurut saya, kelas baru yang dibuat menyebabkan duplikasi kode dimana kita bisa saja menggunakan ` CreateProductFunctionalTest.java`
   untuk mendapatkan hasil yang sama. Jika kita memaksakan untuk tetap membuat class baru tersebut, ini bisa berdampak pada saat maintenance.
   dimana kode tersebut juga harus diubah kembali.

   Adapun usaha untuk memperbaiki hal ini adalah dengan mencoba untuk membuat base class yang berisi setup driver selenium, konfigurasi URL, dan lain lain. Jika ada test yang memiliki pola yang sama dengan variasi kecil (misalnya, input produk yang berbeda), kita bisa menggunakan `@ParameterizedTest` di JUnit untuk menghindari duplikasi logika yang sama.