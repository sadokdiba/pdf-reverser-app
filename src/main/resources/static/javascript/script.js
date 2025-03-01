document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('file-upload').addEventListener('change', function(e) {
        const fileName = e.target.files[0]?.name;
        document.getElementById('selected-file').textContent = fileName || '';
    });

    document.querySelector('form').addEventListener('submit', function(e) {
        e.preventDefault();
        const button = this.querySelector('button');
        const formData = new FormData(this);
        const resultCard = document.getElementById('result-card');
        const progressBar = document.getElementById('progress-bar');
        
        button.innerHTML = '<span style="display: inline-block; animation: spin 1s linear infinite;">↻</span> Processing...';
        button.disabled = true;
        resultCard.style.display = 'block';

        // Simulate progress (replace with actual progress if backend supports it)
        let progress = 0;
        const progressInterval = setInterval(() => {
            progress += 5;
            progressBar.style.width = `${Math.min(progress, 90)}%`;
        }, 200);

        fetch('/api/pdf/reverse', {
            method: 'POST',
            body: formData
        })
        .then(response => response.blob())
        .then(blob => {
            clearInterval(progressInterval);
            progressBar.style.width = '100%';
            
            // Create URL for the blob
            const url = URL.createObjectURL(blob);
            
            // Update preview with PDF icon and filename
            const previewContainer = document.getElementById('pdf-preview');
            const fileName = document.getElementById('file-upload').files[0].name;
            previewContainer.innerHTML = `
                <div class="download-icon">📄</div>
                <div class="pdf-title">${fileName}</div>
            `;

            resultCard.onclick = () => {
                const link = document.createElement('a');
                link.href = url;
                link.download = 'reversed-' + fileName;
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            };

            button.innerHTML = 'Upload and Reverse';
            button.disabled = false;
        })
        .catch(error => {
            clearInterval(progressInterval);
            console.error('Error:', error);
            progressBar.style.width = '0%';
            button.innerHTML = 'Upload and Reverse';
            button.disabled = false;
        });
    });
});