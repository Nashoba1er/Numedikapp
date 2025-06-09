from PIL import Image
import numpy as np

# Couleurs du thème sombre (origine)
dark_colors = {
    "white": (255, 255, 255),
    "pink_primary": (237, 0, 170),
    "navy_background": (25, 26, 89)
}

# Couleurs du thème clair (remplacement)
light_colors = {
    "white": (51, 51, 51),  # dark_text
    "pink_primary": (255, 102, 204),  # pink_primary_light
    "navy_background": (221, 225, 255)  # navy_background_light
}

# Tolérance pour matcher les couleurs
COLOR_TOLERANCE = 30

def color_close(a, b, tolerance=COLOR_TOLERANCE):
    return all(abs(x - y) <= tolerance for x, y in zip(a, b))

def convert_image_theme(input_path, output_path):
    img = Image.open(input_path).convert("RGB")
    data = np.array(img)

    # Préparation du masque de remplacement
    for y in range(data.shape[0]):
        for x in range(data.shape[1]):
            current_color = tuple(data[y, x])
            for name, dark_rgb in dark_colors.items():
                if color_close(current_color, dark_rgb):
                    data[y, x] = light_colors[name]
                    break  # couleur trouvée, on arrête ici

    # Création et sauvegarde
    new_img = Image.fromarray(data.astype(np.uint8))
    new_img.save(output_path)

# Utilisation
convert_image_theme("C:/Users/antoi/Documents_local/dossier_mines_st_etienne/cours/price/application/logo.jpg", "C:/Users/antoi/Documents_local/dossier_mines_st_etienne/cours/price/application/logo_light_theme.jpg")
